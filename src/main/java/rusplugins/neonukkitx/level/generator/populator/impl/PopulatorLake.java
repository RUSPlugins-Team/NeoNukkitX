package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.impl.beach.BeachBiome;
import rusplugins.neonukkitx.level.biome.impl.extremehills.ExtremeHillsBiome;
import rusplugins.neonukkitx.level.biome.impl.extremehills.ExtremeHillsPlusBiome;
import rusplugins.neonukkitx.level.biome.impl.mushroom.MushroomIslandBiome;
import rusplugins.neonukkitx.level.biome.impl.swamp.SwampBiome;
import rusplugins.neonukkitx.level.biome.type.WateryBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Populates lakes in flat/low areas.
 * Lakes are oval-shaped depressions filled with water, with sand/clay bottom.
 */
public class PopulatorLake extends Populator {

    private int lakeRarity = 60; // 1 in 60 chunks

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        if (random.nextBoundedInt(lakeRarity) != 0) {
            return;
        }

        // Try multiple positions
        int attempts = 2 + random.nextBoundedInt(3);
        for (int a = 0; a < attempts; a++) {
            int x = 2 + random.nextBoundedInt(12);
            int z = 2 + random.nextBoundedInt(12);
            int y = chunk.getHighestBlockAt(x, z);

            // Skip if too high or in unsuitable biome
            if (y > 85 || y < 50) {
                continue;
            }

            Biome biome = Biome.getBiome(chunk.getBiomeId(x, z));
            if (biome instanceof WateryBiome || biome instanceof MushroomIslandBiome 
                || biome instanceof ExtremeHillsBiome || biome instanceof ExtremeHillsPlusBiome) {
                continue;
            }

            int size = 3 + random.nextBoundedInt(5); // 3-7 radius
            generateLake(chunk, x, y, z, size, random);
        }
    }

    private void generateLake(FullChunk chunk, int centerX, int surfaceY, int centerZ, int radius, NukkitRandom random) {
        int depth = 2 + random.nextBoundedInt(3); // 2-4 blocks deep
        int waterLevel = surfaceY - 1;

        // Oval shape
        int radiusX = radius;
        int radiusZ = radius - random.nextBoundedInt(2); // slightly oval

        for (int dx = -radiusX; dx <= radiusX; dx++) {
            for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                // Oval equation
                double normalizedDist = ((double) dx * dx) / (radiusX * radiusX) + ((double) dz * dz) / (radiusZ * radiusZ);
                if (normalizedDist > 1.0) {
                    continue;
                }

                int bx = centerX + dx;
                int bz = centerZ + dz;
                if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16) {
                    continue;
                }

                // Find actual surface
                int topY = -1;
                for (int y = surfaceY + 3; y > 0; y--) {
                    int blockId = chunk.getBlockId(bx, y, bz);
                    if (blockId == BlockID.GRASS || blockId == BlockID.DIRT || blockId == BlockID.STONE) {
                        topY = y;
                        break;
                    }
                }

                if (topY < 0) continue;

                // Carve lake bed
                int lakeBottom = topY - depth;
                if (lakeBottom < 1) lakeBottom = 1;

                for (int y = topY; y >= lakeBottom; y--) {
                    if (y == topY) {
                        // Water surface
                        chunk.setBlockId(bx, y, bz, BlockID.STILL_WATER);
                    } else if (y == lakeBottom) {
                        // Bottom: sand or clay
                        chunk.setBlockId(bx, y, bz, BlockID.SAND);
                    } else {
                        // Walls and fill below water
                        int blockId = chunk.getBlockId(bx, y, bz);
                        if (blockId == BlockID.AIR) {
                            chunk.setBlockId(bx, y, bz, BlockID.SAND);
                        } else if (blockId != BlockID.STILL_WATER && blockId != BlockID.WATER) {
                            chunk.setBlockId(bx, y, bz, BlockID.SAND);
                        }
                    }
                }

                // Add sand rim (beach around lake)
                if (normalizedDist > 0.6 && normalizedDist <= 1.0) {
                    if (chunk.getBlockId(bx, topY + 1, bz) == BlockID.AIR) {
                        int rimBlock = (random.nextBoundedInt(100) < 80) ? BlockID.SAND : BlockID.GRAVEL;
                        chunk.setBlockId(bx, topY + 1, bz, rimBlock);
                    }
                }
            }
        }

        // Add reeds/sugar cane on edges
        addLakeDecoration(chunk, centerX, waterLevel, centerZ, radiusX, radiusZ, random);
    }

    private void addLakeDecoration(FullChunk chunk, int centerX, int waterLevel, int centerZ, int radiusX, int radiusZ, NukkitRandom random) {
        for (int i = 0; i < 6; i++) {
            int dx = random.nextBoundedInt(radiusX * 2 + 1) - radiusX;
            int dz = random.nextBoundedInt(radiusZ * 2 + 1) - radiusZ;
            int bx = centerX + dx;
            int bz = centerZ + dz;

            if (bx < 1 || bx >= 15 || bz < 1 || bz >= 15) {
                continue;
            }

            // Check if next to water
            boolean nextToWater = false;
            for (int dir = 0; dir < 4; dir++) {
                int nx = bx + (dir == 1 ? 1 : dir == 3 ? -1 : 0);
                int nz = bz + (dir == 0 ? 1 : dir == 2 ? -1 : 0);
                int nid = chunk.getBlockId(nx, waterLevel, nz);
                if (nid == BlockID.STILL_WATER || nid == BlockID.WATER) {
                    nextToWater = true;
                    break;
                }
            }

            if (nextToWater) {
                // Sugar cane or lily pad
                if (random.nextBoundedInt(100) < 40) {
                    // Sugar cane on sand/dirt next to water
                    if (chunk.getBlockId(bx, waterLevel, bz) == BlockID.SAND || chunk.getBlockId(bx, waterLevel, bz) == BlockID.DIRT) {
                        chunk.setBlockId(bx, waterLevel + 1, bz, BlockID.SUGARCANE_BLOCK);
                    }
                } else if (random.nextBoundedInt(100) < 30) {
                    // Lily pad on water
                    if (chunk.getBlockId(bx, waterLevel, bz) == BlockID.STILL_WATER) {
                        chunk.setBlockId(bx, waterLevel + 1, bz, BlockID.WATER_LILY);
                    }
                }
            }
        }
    }
}
