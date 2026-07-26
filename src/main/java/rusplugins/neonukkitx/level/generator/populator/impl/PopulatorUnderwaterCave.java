package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.type.WateryBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Populates underwater caves in ocean biomes.
 * Caves below sea level with air pockets, magma, and glowstone.
 */
public class PopulatorUnderwaterCave extends Populator {

    private int caveRarity = 50; // 1 in 50 water chunks

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        // Only in water biomes
        Biome centerBiome = Biome.getBiome(chunk.getBiomeId(7, 7));
        if (!(centerBiome instanceof WateryBiome)) {
            return;
        }

        if (random.nextBoundedInt(caveRarity) != 0) {
            return;
        }

        int attempts = 1 + random.nextBoundedInt(2);
        for (int a = 0; a < attempts; a++) {
            int x = 2 + random.nextBoundedInt(12);
            int z = 2 + random.nextBoundedInt(12);
            int surfaceY = chunk.getHighestBlockAt(x, z);

            // Must be underwater
            if (surfaceY > Normal.seaHeight) {
                continue;
            }

            int caveY = 20 + random.nextBoundedInt(25); // 20-45 depth
            int radius = 3 + random.nextBoundedInt(4);

            generateUnderwaterCave(chunk, x, caveY, z, radius, random);
        }
    }

    private void generateUnderwaterCave(FullChunk chunk, int centerX, int centerY, int centerZ, int radius, NukkitRandom random) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius / 2; dy <= radius / 2; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int distSq = dx * dx + dy * dy * 4 + dz * dz;
                    if (distSq > radius * radius) {
                        continue;
                    }

                    int bx = centerX + dx;
                    int by = centerY + dy;
                    int bz = centerZ + dz;
                    if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16 || by < 5 || by > Normal.seaHeight - 2) {
                        continue;
                    }

                    // Don't carve into bedrock
                    if (chunk.getBlockId(bx, by, bz) == BlockID.BEDROCK) {
                        continue;
                    }

                    // Carve cave
                    chunk.setBlockId(bx, by, bz, BlockID.AIR);

                    // Decorate walls
                    if (distSq > (radius - 1) * (radius - 1)) {
                        // Wall blocks
                        int wallBlock;
                        int r = random.nextBoundedInt(100);
                        if (r < 5) {
                            wallBlock = BlockID.GLOWSTONE; // Rare glowstone
                        } else if (r < 15) {
                            wallBlock = BlockID.MAGMA; // Magma
                        } else if (r < 30) {
                            wallBlock = BlockID.GRAVEL; // Gravel
                        } else {
                            continue; // Leave as carved (air pocket)
                        }

                        // Place wall decoration
                        for (int[] dir : new int[][]{{1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}, {0,0,1}, {0,0,-1}}) {
                            int nx = bx + dir[0];
                            int ny = by + dir[1];
                            int nz = bz + dir[2];
                            if (nx >= 0 && nx < 16 && nz >= 0 && nz < 16 && ny > 0 && ny < 250) {
                                if (chunk.getBlockId(nx, ny, nz) == BlockID.STONE) {
                                    chunk.setBlockId(nx, ny, nz, wallBlock);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add air pocket at top (breathable space)
        int airRadius = radius / 2;
        for (int dx = -airRadius; dx <= airRadius; dx++) {
            for (int dz = -airRadius; dz <= airRadius; dz++) {
                if (dx * dx + dz * dz > airRadius * airRadius) {
                    continue;
                }
                int bx = centerX + dx;
                int bz = centerZ + dz;
                if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16) {
                    continue;
                }
                
                // Clear water at top of cave
                for (int y = centerY + radius / 2; y < Normal.seaHeight; y++) {
                    if (chunk.getBlockId(bx, y, bz) == BlockID.STILL_WATER || chunk.getBlockId(bx, y, bz) == BlockID.WATER) {
                        chunk.setBlockId(bx, y, bz, BlockID.AIR);
                    }
                }
            }
        }
    }
}
