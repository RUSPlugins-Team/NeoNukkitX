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
 * Populates waterfalls on cliff faces in extreme hills.
 * Waterfalls flow from high points down to lower terrain or rivers.
 */
public class PopulatorWaterfall extends Populator {

    private int waterfallRarity = 25; // 1 in 25 chunks in hills

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        // Only in extreme hills
        Biome centerBiome = Biome.getBiome(chunk.getBiomeId(7, 7));
        if (!(centerBiome instanceof ExtremeHillsBiome) && !(centerBiome instanceof ExtremeHillsPlusBiome)) {
            return;
        }

        if (random.nextBoundedInt(waterfallRarity) != 0) {
            return;
        }

        // Find cliff edges
        int attempts = 3 + random.nextBoundedInt(5);
        for (int a = 0; a < attempts; a++) {
            int x = 2 + random.nextBoundedInt(12);
            int z = 2 + random.nextBoundedInt(12);
            int y = chunk.getHighestBlockAt(x, z);

            if (y < 80 || y > 200) {
                continue;
            }

            // Check if there's a drop nearby
            int dropX = -1, dropZ = -1, dropHeight = 0;
            int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int nz = z + dir[1];
                if (nx < 0 || nx >= 16 || nz < 0 || nz >= 16) {
                    continue;
                }
                
                int neighborY = chunk.getHighestBlockAt(nx, nz);
                if (neighborY < y - 5) {
                    // Found a drop
                    dropX = nx;
                    dropZ = nz;
                    dropHeight = y - neighborY;
                    break;
                }
            }

            if (dropX >= 0 && dropHeight >= 5) {
                generateWaterfall(chunk, x, y, z, dropX, dropZ, dropHeight, random);
            }
        }
    }

    private void generateWaterfall(FullChunk chunk, int topX, int topY, int topZ, int dropX, int dropZ, int dropHeight, NukkitRandom random) {
        // Determine waterfall direction
        int dx = dropX - topX;
        int dz = dropZ - topZ;

        // Water source at top
        chunk.setBlockId(topX, topY + 1, topZ, BlockID.WATER);

        // Water flowing down the cliff
        int cx = topX;
        int cz = topZ;
        int cy = topY;

        for (int step = 0; step < dropHeight + 3 && step < 30; step++) {
            // Move in drop direction
            if (step % 2 == 0 && step < dropHeight) {
                cx += dx;
                cz += dz;
            }
            cy--;

            if (cx < 0 || cx >= 16 || cz < 0 || cz >= 16 || cy < 1) {
                break;
            }

            int blockId = chunk.getBlockId(cx, cy, cz);
            
            // Place water
            if (blockId == BlockID.AIR) {
                chunk.setBlockId(cx, cy, cz, BlockID.WATER);
            } else if (blockId == BlockID.STONE || blockId == BlockID.DIRT || blockId == BlockID.GRASS) {
                // Water flowing over rock — keep going
                chunk.setBlockId(cx, cy + 1, cz, BlockID.WATER);
            } else if (blockId == BlockID.STILL_WATER || blockId == BlockID.WATER) {
                // Reached water pool/river
                break;
            }

            // Add mossy cobblestone or wet stone around waterfall (aesthetic)
            if (random.nextBoundedInt(100) < 20) {
                addWetStone(chunk, cx, cy, cz, random);
            }
        }

        // Pool at bottom
        createPoolAtBottom(chunk, cx, cy, cz, random);
    }

    private void addWetStone(FullChunk chunk, int x, int y, int z, NukkitRandom random) {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int nz = z + dir[1];
            if (nx < 0 || nx >= 16 || nz < 0 || nz >= 16) {
                continue;
            }
            
            if (chunk.getBlockId(nx, y, nz) == BlockID.STONE && random.nextBoundedInt(100) < 40) {
                // Use cobblestone as mossy/wet stone (Bedrock limitation)
                chunk.setBlockId(nx, y, nz, BlockID.COBBLESTONE);
            }
        }
    }

    private void createPoolAtBottom(FullChunk chunk, int x, int y, int z, NukkitRandom random) {
        int poolRadius = 2 + random.nextBoundedInt(2);
        
        for (int dx = -poolRadius; dx <= poolRadius; dx++) {
            for (int dz = -poolRadius; dz <= poolRadius; dz++) {
                if (dx * dx + dz * dz > poolRadius * poolRadius) {
                    continue;
                }
                
                int bx = x + dx;
                int bz = z + dz;
                if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16) {
                    continue;
                }
                
                // Fill with water
                for (int py = y; py <= y + 1; py++) {
                    if (py > 0 && py < 250) {
                        int blockId = chunk.getBlockId(bx, py, bz);
                        if (blockId == BlockID.AIR || blockId == BlockID.GRASS || blockId == BlockID.DIRT) {
                            chunk.setBlockId(bx, py, bz, BlockID.STILL_WATER);
                        }
                    }
                }
                
                // Sand bottom
                int bottomId = chunk.getBlockId(bx, y - 1, bz);
                if (bottomId == BlockID.DIRT || bottomId == BlockID.GRASS || bottomId == BlockID.STONE) {
                    chunk.setBlockId(bx, y - 1, bz, BlockID.SAND);
                }
            }
        }
    }
}
