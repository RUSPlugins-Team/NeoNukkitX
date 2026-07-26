package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.block.BlockSapling;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.EnumBiome;
import rusplugins.neonukkitx.level.biome.impl.swamp.SwampBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.object.tree.ObjectTree;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Populates craters — forest craters (filled with trees/grass) and swamp craters (filled with water/mud).
 */
public class PopulatorCrater extends Populator {

    // Crater rarity: lower = more common (1 in N chunks)
    private int craterRarity = 180;
    // Max craters per chunk area
    private int maxCratersPerChunk = 1;

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        if (random.nextBoundedInt(craterRarity) != 0) {
            return;
        }

        int count = random.nextBoundedInt(maxCratersPerChunk) + 1;
        for (int i = 0; i < count; i++) {
            int x = random.nextBoundedInt(16);
            int z = random.nextBoundedInt(16);
            int y = chunk.getHighestBlockAt(x, z);

            if (y < Normal.seaHeight + 5 || y > 200) {
                continue;
            }

            Biome biome = Biome.getBiome(chunk.getBiomeId(x, z));
            boolean isSwamp = (biome instanceof SwampBiome);

            generateCrater(chunk, x, y, z, random, isSwamp);
        }
    }

    private void generateCrater(FullChunk chunk, int centerX, int surfaceY, int centerZ, NukkitRandom random, boolean isSwamp) {
        int radius = 4 + random.nextBoundedInt(5); // 4-8 blocks radius
        int depth = 3 + random.nextBoundedInt(4);   // 3-6 blocks deep

        // Carve the crater bowl
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq > radius * radius) {
                    continue;
                }

                int bx = centerX + dx;
                int bz = centerZ + dz;
                if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16) {
                    continue;
                }

                // Calculate depth at this position (parabolic shape)
                double normalizedDist = Math.sqrt(distSq) / radius;
                int localDepth = (int) (depth * (1.0 - normalizedDist * normalizedDist));

                if (localDepth < 1) {
                    localDepth = 1;
                }

                int topY = surfaceY;
                // Find actual surface
                for (int y = surfaceY + 5; y > 0; y--) {
                    int blockId = chunk.getBlockId(bx, y, bz);
                    if (blockId == BlockID.GRASS || blockId == BlockID.DIRT || blockId == BlockID.STONE) {
                        topY = y;
                        break;
                    }
                }

                // Carve down
                for (int d = 0; d <= localDepth; d++) {
                    int carveY = topY - d;
                    if (carveY < 1) {
                        break;
                    }

                    if (d == localDepth) {
                        // Bottom of crater
                        if (isSwamp) {
                            chunk.setBlockId(bx, carveY, bz, BlockID.MYCELIUM);
                            // Fill with water if deep enough
                            if (localDepth >= 3 && carveY < Normal.seaHeight + 2) {
                                for (int wy = carveY + 1; wy <= topY; wy++) {
                                    chunk.setBlockId(bx, wy, bz, BlockID.STILL_WATER);
                                }
                            }
                        } else {
                            chunk.setBlockId(bx, carveY, bz, BlockID.GRASS);
                        }
                    } else if (d == localDepth - 1 && !isSwamp) {
                        // One block above bottom — dirt for trees
                        chunk.setBlockId(bx, carveY, bz, BlockID.DIRT);
                    } else {
                        // Walls and fill
                        if (isSwamp) {
                            chunk.setBlockId(bx, carveY, bz, BlockID.DIRT);
                        } else {
                            chunk.setBlockId(bx, carveY, bz, BlockID.STONE);
                        }
                    }
                }

                // Fill swamp craters with water
                if (isSwamp && localDepth >= 2) {
                    int waterLevel = topY - localDepth / 2;
                    for (int wy = waterLevel; wy <= topY; wy++) {
                        if (chunk.getBlockId(bx, wy, bz) != BlockID.DIRT && chunk.getBlockId(bx, wy, bz) != BlockID.MYCELIUM) {
                            chunk.setBlockId(bx, wy, bz, BlockID.STILL_WATER);
                        }
                    }
                }
            }
        }

        // Decorate crater interior
        if (!isSwamp) {
            // Forest crater: add trees and tall grass
            int innerRadius = Math.max(1, radius - 2);
            for (int dx = -innerRadius; dx <= innerRadius; dx++) {
                for (int dz = -innerRadius; dz <= innerRadius; dz++) {
                    int distSq = dx * dx + dz * dz;
                    if (distSq > innerRadius * innerRadius) {
                        continue;
                    }

                    int bx = centerX + dx;
                    int bz = centerZ + dz;
                    if (bx < 1 || bx >= 15 || bz < 1 || bz >= 15) {
                        continue;
                    }

                    // Find grass surface inside crater
                    int groundY = -1;
                    for (int y = surfaceY; y > surfaceY - depth - 2; y--) {
                        if (chunk.getBlockId(bx, y, bz) == BlockID.GRASS) {
                            groundY = y;
                            break;
                        }
                    }

                    if (groundY > 0) {
                        // Place tree with 30% chance
                        if (random.nextBoundedInt(100) < 30) {
                            int treeType = random.nextBoundedInt(3);
                            int saplingMeta;
                            switch (treeType) {
                                case 0: saplingMeta = BlockSapling.OAK; break;
                                case 1: saplingMeta = BlockSapling.BIRCH; break;
                                default: saplingMeta = BlockSapling.SPRUCE; break;
                            }
                            // Place sapling and grow tree
                            chunk.setBlockId(bx, groundY + 1, bz, BlockID.SAPLING);
                            chunk.setBlockData(bx, groundY + 1, bz, saplingMeta);
                        } else if (random.nextBoundedInt(100) < 50) {
                            // Tall grass
                            chunk.setBlockId(bx, groundY + 1, bz, BlockID.TALL_GRASS);
                            chunk.setBlockData(bx, groundY + 1, bz, 1); // tall grass variant
                        }
                    }
                }
            }
        } else {
            // Swamp crater: add lily pads and swamp trees
            for (int dx = -radius + 1; dx <= radius - 1; dx++) {
                for (int dz = -radius + 1; dz <= radius - 1; dz++) {
                    int bx = centerX + dx;
                    int bz = centerZ + dz;
                    if (bx < 1 || bx >= 15 || bz < 1 || bz >= 15) {
                        continue;
                    }

                    // Find water surface
                    int waterY = -1;
                    for (int y = surfaceY; y > surfaceY - depth - 2; y--) {
                        if (chunk.getBlockId(bx, y, bz) == BlockID.STILL_WATER) {
                            waterY = y;
                            break;
                        }
                    }

                    if (waterY > 0) {
                        // Lily pad with 15% chance
                        if (random.nextBoundedInt(100) < 15) {
                            chunk.setBlockId(bx, waterY + 1, bz, BlockID.WATER_LILY);
                        }
                        // Swamp tree with 10% chance (on dirt/mycelium spots)
                        if (random.nextBoundedInt(100) < 10) {
                            int groundY = waterY - 1;
                            if (chunk.getBlockId(bx, groundY, bz) == BlockID.DIRT || chunk.getBlockId(bx, groundY, bz) == BlockID.MYCELIUM) {
                                chunk.setBlockId(bx, waterY, bz, BlockID.DIRT); // remove water for tree base
                                chunk.setBlockId(bx, waterY + 1, bz, BlockID.SAPLING);
                                chunk.setBlockData(bx, waterY + 1, bz, BlockSapling.OAK);
                            }
                        }
                    }
                }
            }
        }

        // Rim: raise edges slightly for realism
        int rimRadius = radius + 1;
        for (int dx = -rimRadius; dx <= rimRadius; dx++) {
            for (int dz = -rimRadius; dz <= rimRadius; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq <= radius * radius || distSq > rimRadius * rimRadius) {
                    continue;
                }

                int bx = centerX + dx;
                int bz = centerZ + dz;
                if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16) {
                    continue;
                }

                int topY = chunk.getHighestBlockAt(bx, bz);
                if (topY > 0 && topY < 254) {
                    // Raise rim by 1 block
                    int blockId = chunk.getBlockId(bx, topY, bz);
                    if (blockId == BlockID.GRASS || blockId == BlockID.DIRT) {
                        chunk.setBlockId(bx, topY + 1, bz, blockId);
                    }
                }
            }
        }
    }
}
