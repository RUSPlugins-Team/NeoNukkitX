package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.EnumBiome;
import rusplugins.neonukkitx.level.biome.type.WateryBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Populates coral reefs in warm ocean biomes.
 * Coral blocks and coral fans on the ocean floor.
 */
public class PopulatorCoral extends Populator {

    private int coralRarity = 30; // 1 in 30 warm ocean chunks

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        // Only in water biomes
        Biome biome = Biome.getBiome(chunk.getBiomeId(7, 7));
        if (!(biome instanceof WateryBiome)) {
            return;
        }

        // Check if warm enough (warm ocean or lukewarm)
        int biomeId = chunk.getBiomeId(7, 7);
        if (biomeId != EnumBiome.WARM_OCEAN.id && biomeId != EnumBiome.LUKEWARM_OCEAN.id
            && biomeId != EnumBiome.DEEP_LUKEWARM_OCEAN.id && biomeId != EnumBiome.DEEP_WARM_OCEAN.id) {
            return;
        }

        if (random.nextBoundedInt(coralRarity) != 0) {
            return;
        }

        int patches = 2 + random.nextBoundedInt(4);
        for (int p = 0; p < patches; p++) {
            int x = 2 + random.nextBoundedInt(12);
            int z = 2 + random.nextBoundedInt(12);
            int surfaceY = chunk.getHighestBlockAt(x, z);

            // Must be underwater
            if (surfaceY > Normal.seaHeight || surfaceY < 30) {
                continue;
            }

            generateCoralPatch(chunk, x, surfaceY, z, random);
        }
    }

    private void generateCoralPatch(FullChunk chunk, int centerX, int floorY, int centerZ, NukkitRandom random) {
        int radius = 2 + random.nextBoundedInt(4);

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

                // Find ocean floor
                int floor = -1;
                for (int y = Normal.seaHeight; y > 10; y--) {
                    int blockId = chunk.getBlockId(bx, y, bz);
                    if (blockId == BlockID.SAND || blockId == BlockID.GRAVEL || blockId == BlockID.STONE) {
                        floor = y;
                        break;
                    }
                }

                if (floor < 0) continue;

                // Chance to place coral
                if (random.nextBoundedInt(100) < 40) {
                    // Place coral block on floor
                    chunk.setBlockId(bx, floor + 1, bz, BlockID.CORAL_BLOCK);

                    // Sometimes stack coral blocks
                    int height = 1 + random.nextBoundedInt(3);
                    for (int h = 1; h < height && floor + 1 + h < Normal.seaHeight; h++) {
                        if (random.nextBoundedInt(100) < 60) {
                            chunk.setBlockId(bx, floor + 1 + h, bz, BlockID.CORAL_BLOCK);
                        }
                    }

                    // Add coral fan on sides
                    if (random.nextBoundedInt(100) < 50) {
                        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
                        int[] dir = dirs[random.nextBoundedInt(dirs.length)];
                        int fx = bx + dir[0];
                        int fz = bz + dir[1];
                        if (fx >= 0 && fx < 16 && fz >= 0 && fz < 16) {
                            chunk.setBlockId(fx, floor + 1, fz, BlockID.CORAL_FAN);
                        }
                    }
                }

                // Add sea pickles
                if (random.nextBoundedInt(100) < 10) {
                    chunk.setBlockId(bx, floor + 1, bz, BlockID.SEA_PICKLE);
                }

                // Add dead coral fan rarely
                if (random.nextBoundedInt(100) < 5) {
                    chunk.setBlockId(bx, floor + 1, bz, BlockID.CORAL_FAN_DEAD);
                }
            }
        }
    }
}
