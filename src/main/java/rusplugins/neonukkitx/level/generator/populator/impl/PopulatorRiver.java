package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.EnumBiome;
import rusplugins.neonukkitx.level.biome.impl.extremehills.ExtremeHillsBiome;
import rusplugins.neonukkitx.level.biome.impl.extremehills.ExtremeHillsPlusBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Populates rivers — water channels that flow through terrain.
 * Rivers start from high ground and flow downward, creating valleys.
 */
public class PopulatorRiver extends Populator {

    private int riverRarity = 45; // 1 in 45 chunks

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        if (random.nextBoundedInt(riverRarity) != 0) {
            return;
        }

        // Find starting point — prefer edges or high ground
        int startX, startZ;
        int edge = random.nextBoundedInt(4);
        
        switch (edge) {
            case 0: startX = random.nextBoundedInt(16); startZ = 0; break;
            case 1: startX = 15; startZ = random.nextBoundedInt(16); break;
            case 2: startX = random.nextBoundedInt(16); startZ = 15; break;
            default: startX = 0; startZ = random.nextBoundedInt(16); break;
        }

        int startY = chunk.getHighestBlockAt(startX, startZ);
        if (startY < 60) {
            return;
        }

        // River path length
        int length = 10 + random.nextBoundedInt(20);
        int width = 2 + random.nextBoundedInt(2); // 2-3 blocks wide

        generateRiverPath(chunk, startX, startY, startZ, length, width, random);
    }

    private void generateRiverPath(FullChunk chunk, int startX, int startY, int startZ, int length, int width, NukkitRandom random) {
        int cx = startX;
        int cy = startY;
        int cz = startZ;

        for (int step = 0; step < length; step++) {
            if (cx < 1 || cx >= 15 || cz < 1 || cz >= 15) {
                break;
            }

            // River bed at current position
            carveRiverSegment(chunk, cx, cy, cz, width, random);

            // Move downstream — try to go down
            int bestY = cy;
            int bestX = cx;
            int bestZ = cz;
            boolean foundLower = false;

            // Check 4 directions + down
            int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : dirs) {
                int nx = cx + dir[0];
                int nz = cz + dir[1];
                if (nx < 1 || nx >= 15 || nz < 1 || nz >= 15) {
                    continue;
                }
                
                int ny = chunk.getHighestBlockAt(nx, nz);
                if (ny < bestY) {
                    bestY = ny;
                    bestX = nx;
                    bestZ = nz;
                    foundLower = true;
                }
            }

            if (!foundLower) {
                // Try to keep going in same general direction
                bestX = cx + random.nextBoundedInt(3) - 1;
                bestZ = cz + random.nextBoundedInt(3) - 1;
                bestY = chunk.getHighestBlockAt(bestX, bestZ);
            }

            cx = bestX;
            cz = bestZ;
            cy = Math.max(bestY, cy - 1); // Can step down 1 block

            // Stop if we hit water level
            if (cy <= Normal.seaHeight) {
                break;
            }
        }
    }

    private void carveRiverSegment(FullChunk chunk, int centerX, int y, int centerZ, int width, NukkitRandom random) {
        int radius = width / 2;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int bx = centerX + dx;
                int bz = centerZ + dz;
                if (bx < 0 || bx >= 16 || bz < 0 || bz >= 16) {
                    continue;
                }

                int distSq = dx * dx + dz * dz;
                if (distSq > radius * radius + 1) {
                    continue;
                }

                // Find surface
                int surfaceY = -1;
                for (int checkY = y + 5; checkY > 0; checkY--) {
                    int blockId = chunk.getBlockId(bx, checkY, bz);
                    if (blockId == BlockID.GRASS || blockId == BlockID.DIRT || blockId == BlockID.STONE) {
                        surfaceY = checkY;
                        break;
                    }
                }

                if (surfaceY < 0) continue;

                // Carve river bed — 1-2 blocks deep
                int bedDepth = 1 + random.nextBoundedInt(2);
                int riverBottom = surfaceY - bedDepth;
                if (riverBottom < 1) riverBottom = 1;

                for (int by = surfaceY; by >= riverBottom; by--) {
                    if (by == surfaceY) {
                        chunk.setBlockId(bx, by, bz, BlockID.STILL_WATER);
                    } else {
                        // River bed: sand or gravel
                        int bedBlock = (random.nextBoundedInt(100) < 70) ? BlockID.SAND : BlockID.GRAVEL;
                        chunk.setBlockId(bx, by, bz, bedBlock);
                    }
                }

                // Check for waterfall — if there's a drop ahead
                checkWaterfall(chunk, bx, surfaceY, bz, random);
            }
        }
    }

    /**
     * Checks if there's a drop and creates a waterfall effect.
     */
    private void checkWaterfall(FullChunk chunk, int x, int y, int z, NukkitRandom random) {
        // Check if block below river is air or much lower
        if (y > 2) {
            int belowId = chunk.getBlockId(x, y - 2, z);
            if (belowId == BlockID.AIR || belowId == BlockID.STILL_WATER) {
                // This is a drop — ensure water flows down
                for (int dy = y - 1; dy > y - 5 && dy > 0; dy--) {
                    if (chunk.getBlockId(x, dy, z) == BlockID.AIR) {
                        chunk.setBlockId(x, dy, z, BlockID.WATER);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
