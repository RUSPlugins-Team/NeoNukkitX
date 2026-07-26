package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.impl.extremehills.ExtremeHillsBiome;
import rusplugins.neonukkitx.level.biome.impl.extremehills.ExtremeHillsPlusBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Populates ravines/gorges between mountains.
 * Deep narrow cracks with water at the bottom, sometimes with waterfalls.
 */
public class PopulatorRavine extends Populator {

    private int ravineRarity = 80; // 1 in 80 chunks in hills

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        // Only in extreme hills
        Biome centerBiome = Biome.getBiome(chunk.getBiomeId(7, 7));
        if (!(centerBiome instanceof ExtremeHillsBiome) && !(centerBiome instanceof ExtremeHillsPlusBiome)) {
            return;
        }

        if (random.nextBoundedInt(ravineRarity) != 0) {
            return;
        }

        // Start from edge of chunk — ravine crosses through
        int startX = random.nextBoundedInt(16);
        int startZ = random.nextBoundedInt(16);
        int startY = chunk.getHighestBlockAt(startX, startZ);

        if (startY < 70) {
            return;
        }

        int length = 15 + random.nextBoundedInt(20);
        int width = 2 + random.nextBoundedInt(2);
        int depth = 15 + random.nextBoundedInt(25);

        generateRavine(chunk, startX, startY, startZ, length, width, depth, random);
    }

    private void generateRavine(FullChunk chunk, int startX, int startY, int startZ, int length, int width, int depth, NukkitRandom random) {
        // Direction: mostly straight with slight curves
        double angle = random.nextBoundedInt(360) * Math.PI / 180;
        double dx = Math.cos(angle);
        double dz = Math.sin(angle);

        int cx = startX;
        int cy = startY;
        int cz = startZ;

        for (int step = 0; step < length; step++) {
            if (cx < 0 || cx >= 16 || cz < 0 || cz >= 16) {
                break;
            }

            // V-shape ravine
            int currentWidth = width + random.nextBoundedInt(2);
            int currentDepth = Math.min(depth, cy - 20);

            for (int rx = -currentWidth; rx <= currentWidth; rx++) {
                for (int rz = -currentWidth; rz <= currentWidth; rz++) {
                    int bx = cx + rx;
                    int bz = cz + rz;
                    if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16) {
                        continue;
                    }

                    // V-shape: narrower at bottom
                    int distFromCenter = Math.max(Math.abs(rx), Math.abs(rz));
                    int localDepth = currentDepth - distFromCenter * 2;
                    if (localDepth < 3) localDepth = 3;

                    int bottomY = cy - localDepth;
                    if (bottomY < 5) bottomY = 5;

                    // Carve ravine
                    for (int y = cy; y >= bottomY; y--) {
                        int blockId = chunk.getBlockId(bx, y, bz);
                        
                        if (y == bottomY) {
                            // Bottom: stone with water pool
                            chunk.setBlockId(bx, y, bz, BlockID.STONE);
                            if (random.nextBoundedInt(100) < 30) {
                                chunk.setBlockId(bx, y, bz, BlockID.GRAVEL);
                            }
                        } else if (y == bottomY + 1 && bottomY < Normal.seaHeight - 5) {
                            // Water at bottom if deep enough
                            chunk.setBlockId(bx, y, bz, BlockID.STILL_WATER);
                        } else if (y > bottomY + 1) {
                            // Air (carved space)
                            if (blockId != BlockID.BEDROCK) {
                                chunk.setBlockId(bx, y, bz, BlockID.AIR);
                            }
                        }
                    }

                    // Waterfall if there's a drop
                    if (distFromCenter == 0 && step > 0 && step < length - 1) {
                        int nextY = cy - 1;
                        if (nextY > bottomY + 2) {
                            // Small waterfall
                            if (random.nextBoundedInt(100) < 15) {
                                for (int wy = cy; wy > bottomY + 1; wy--) {
                                    if (chunk.getBlockId(bx, wy, bz) == BlockID.AIR) {
                                        chunk.setBlockId(bx, wy, bz, BlockID.WATER);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Move forward
            cx += (int) dx;
            cz += (int) dz;
            cy -= 1 + random.nextBoundedInt(2); // Gradually go down

            // Slight curve
            angle += (random.nextBoundedInt(100) - 50) / 200.0;
            dx = Math.cos(angle);
            dz = Math.sin(angle);
        }
    }
}
