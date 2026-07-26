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
 * Populates underwater ruins on ocean floors.
 * Small stone brick structures, sometimes with mossy variants.
 */
public class PopulatorOceanRuin extends Populator {

    private int ruinRarity = 120; // 1 in 120 ocean chunks

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        Biome centerBiome = Biome.getBiome(chunk.getBiomeId(7, 7));
        if (!(centerBiome instanceof WateryBiome)) {
            return;
        }

        if (random.nextBoundedInt(ruinRarity) != 0) {
            return;
        }

        int x = 3 + random.nextBoundedInt(10);
        int z = 3 + random.nextBoundedInt(10);
        int surfaceY = chunk.getHighestBlockAt(x, z);

        if (surfaceY > Normal.seaHeight || surfaceY < 25) {
            return;
        }

        // Find floor
        int floorY = -1;
        for (int y = Normal.seaHeight; y > 10; y--) {
            int blockId = chunk.getBlockId(x, y, z);
            if (blockId == BlockID.SAND || blockId == BlockID.GRAVEL || blockId == BlockID.STONE) {
                floorY = y;
                break;
            }
        }

        if (floorY < 0) return;

        int ruinType = random.nextBoundedInt(3);
        switch (ruinType) {
            case 0:
                generateSmallRuin(chunk, x, floorY, z, random);
                break;
            case 1:
                generateWallRuin(chunk, x, floorY, z, random);
                break;
            default:
                generatePillarRuin(chunk, x, floorY, z, random);
                break;
        }
    }

    private void generateSmallRuin(FullChunk chunk, int cx, int cy, int cz, NukkitRandom random) {
        int size = 2 + random.nextBoundedInt(3);
        int mossyChance = 30;

        for (int dx = -size; dx <= size; dx++) {
            for (int dz = -size; dz <= size; dz++) {
                for (int dy = 0; dy <= size; dy++) {
                    int bx = cx + dx;
                    int by = cy + dy;
                    int bz = cz + dz;
                    if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16 || by > Normal.seaHeight) {
                        continue;
                    }

                    // Random ruin shape
                    if (random.nextBoundedInt(100) < 60) {
                        int block = (random.nextBoundedInt(100) < mossyChance) ? BlockID.MOSSY_STONE : BlockID.STONE_BRICKS;
                        if (dy == 0 || (Math.abs(dx) == size && Math.abs(dz) == size)) {
                            chunk.setBlockId(bx, by, bz, block);
                        } else if (random.nextBoundedInt(100) < 40) {
                            chunk.setBlockId(bx, by, bz, block);
                        }
                    }
                }
            }
        }

        // Add chest rarely
        if (random.nextBoundedInt(100) < 15) {
            chunk.setBlockId(cx, cy + 1, cz, BlockID.CHEST);
        }
    }

    private void generateWallRuin(FullChunk chunk, int cx, int cy, int cz, NukkitRandom random) {
        int length = 3 + random.nextBoundedInt(5);
        int height = 2 + random.nextBoundedInt(3);
        boolean isX = random.nextBoundedInt(2) == 0;

        for (int i = -length / 2; i <= length / 2; i++) {
            for (int h = 0; h < height; h++) {
                int bx = isX ? cx + i : cx;
                int by = cy + h;
                int bz = isX ? cz : cz + i;
                if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16 || by > Normal.seaHeight) {
                    continue;
                }

                int block = (random.nextBoundedInt(100) < 25) ? BlockID.MOSSY_STONE : BlockID.STONE_BRICKS;
                if (random.nextBoundedInt(100) < 70) {
                    chunk.setBlockId(bx, by, bz, block);
                }
            }
        }
    }

    private void generatePillarRuin(FullChunk chunk, int cx, int cy, int cz, NukkitRandom random) {
        int height = 3 + random.nextBoundedInt(4);

        for (int h = 0; h < height; h++) {
            int by = cy + h;
            if (by > Normal.seaHeight || cx < 0 || cx >= 16 || cz < 0 || cz >= 16) {
                continue;
            }

            int block = (h == height - 1) ? BlockID.STONE_BRICKS : 
                        (random.nextBoundedInt(100) < 20) ? BlockID.MOSSY_STONE : BlockID.STONE_BRICKS;
            chunk.setBlockId(cx, by, cz, block);

            // Thicker base
            if (h < 2) {
                for (int[] dir : new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}}) {
                    int nx = cx + dir[0];
                    int nz = cz + dir[1];
                    if (nx >= 0 && nx < 16 && nz >= 0 && nz < 16) {
                        chunk.setBlockId(nx, by, nz, BlockID.STONE_BRICKS);
                    }
                }
            }
        }
    }
}
