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
 * Populates massive volcanoes with a lava-filled rift/crack.
 * Volcanoes spawn centered in chunks to avoid being cut off.
 */
public class PopulatorVolcano extends Populator {

    // Volcano rarity: 1 in N chunks
    private int volcanoRarity = 350;

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        if (random.nextBoundedInt(volcanoRarity) != 0) {
            return;
        }

        // Center volcano in chunk to avoid being cut off at chunk borders
        int centerX = 8;
        int centerZ = 8;
        int surfaceY = chunk.getHighestBlockAt(centerX, centerZ);

        // Only spawn in extreme hills or very high terrain
        Biome biome = Biome.getBiome(chunk.getBiomeId(centerX, centerZ));
        boolean isHills = (biome instanceof ExtremeHillsBiome) || (biome instanceof ExtremeHillsPlusBiome);
        
        if (!isHills && surfaceY < 75) {
            return;
        }

        if (surfaceY < 50 || surfaceY > 160) {
            return;
        }

        generateVolcano(level, chunk, centerX, surfaceY, centerZ, random);
    }

    private void generateVolcano(ChunkManager level, FullChunk chunk, int centerX, int surfaceY, int centerZ, NukkitRandom random) {
        // Massive volcano dimensions — fits within single chunk
        int baseRadius = 6 + random.nextBoundedInt(4);     // 6-9 (fits in chunk)
        int height = 50 + random.nextBoundedInt(35);        // 50-85 blocks tall
        int riftWidth = 2 + random.nextBoundedInt(2);       // 2-3 blocks wide
        
        // Direction of the rift (0-3: N, E, S, W)
        int riftDirection = random.nextBoundedInt(4);
        
        int peakY = surfaceY + height;
        if (peakY > 245) {
            peakY = 245;
            height = peakY - surfaceY;
        }

        // Build the cone
        for (int dy = 0; dy <= height; dy++) {
            int currentY = surfaceY + dy;
            
            // Cone tapers as we go up
            double progress = (double) dy / height;
            double currentRadius = baseRadius * (1.0 - Math.pow(progress, 0.6));
            int r = (int) Math.ceil(currentRadius);
            if (r < 2) r = 2;

            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    int distSq = dx * dx + dz * dz;
                    if (distSq > r * r) {
                        continue;
                    }

                    int bx = centerX + dx;
                    int bz = centerZ + dz;
                    
                    // Allow slight overflow into neighboring chunks via ChunkManager
                    FullChunk targetChunk = chunk;
                    int localX = bx;
                    int localZ = bz;
                    
                    if (bx < 0) {
                        targetChunk = level.getChunk(chunk.getX() - 1, chunk.getZ());
                        localX = bx + 16;
                    } else if (bx >= 16) {
                        targetChunk = level.getChunk(chunk.getX() + 1, chunk.getZ());
                        localX = bx - 16;
                    }
                    
                    if (bz < 0) {
                        targetChunk = level.getChunk(chunk.getX(), chunk.getZ() - 1);
                        localZ = bz + 16;
                    } else if (bz >= 16) {
                        targetChunk = level.getChunk(chunk.getX(), chunk.getZ() + 1);
                        localZ = bz - 16;
                    }
                    
                    if (targetChunk == null) {
                        continue;
                    }

                    // Determine if this is part of the rift
                    boolean isRift = isRiftBlock(dx, dz, r, riftDirection, riftWidth, progress);

                    // Block type
                    int blockId;
                    if (isRift) {
                        // Rift is filled with lava/magma
                        blockId = BlockID.LAVA;
                    } else if (progress > 0.85) {
                        // Near peak: obsidian and coal blocks (basalt-like)
                        if (random.nextBoundedInt(100) < 40) {
                            blockId = BlockID.OBSIDIAN;
                        } else if (random.nextBoundedInt(100) < 30) {
                            blockId = BlockID.COAL_BLOCK;
                        } else {
                            blockId = BlockID.STONE;
                        }
                    } else if (progress > 0.5) {
                        // Upper cone: more magma and obsidian
                        if (random.nextBoundedInt(100) < 25) {
                            blockId = BlockID.MAGMA;
                        } else if (random.nextBoundedInt(100) < 20) {
                            blockId = BlockID.OBSIDIAN;
                        } else {
                            blockId = BlockID.STONE;
                        }
                    } else {
                        // Lower cone: stone with occasional magma
                        if (random.nextBoundedInt(100) < 10) {
                            blockId = BlockID.MAGMA;
                        } else {
                            blockId = BlockID.STONE;
                        }
                    }

                    // Place block
                    targetChunk.setBlockId(localX, currentY, localZ, blockId);

                    // Fill below to avoid floating
                    if (dy == 0) {
                        for (int fillY = currentY - 1; fillY > currentY - 5 && fillY > 0; fillY--) {
                            if (targetChunk.getBlockId(localX, fillY, localZ) == BlockID.AIR) {
                                targetChunk.setBlockId(localX, fillY, localZ, BlockID.STONE);
                            }
                        }
                    }
                }
            }
        }

        // Add lava glow at rift edges and fire at peak
        addVolcanoEffects(level, chunk, centerX, peakY, centerZ, riftDirection, random);
        
        // Ash/gravel ring around base
        addAshRing(level, chunk, centerX, surfaceY, centerZ, baseRadius + 3, random);
    }

    /**
     * Determines if a block position is part of the lava rift.
     */
    private boolean isRiftBlock(int dx, int dz, int radius, int direction, int width, double heightProgress) {
        // Rift only in upper 70% of volcano
        if (heightProgress < 0.3) {
            return false;
        }

        // Rift goes from center outward in one direction
        int absDx = Math.abs(dx);
        int absDz = Math.abs(dz);
        
        switch (direction) {
            case 0: // North (-Z)
                return dz < 0 && absDx <= width / 2 && Math.abs(dz) <= radius * 0.8;
            case 1: // East (+X)
                return dx > 0 && absDz <= width / 2 && dx <= radius * 0.8;
            case 2: // South (+Z)
                return dz > 0 && absDx <= width / 2 && dz <= radius * 0.8;
            case 3: // West (-X)
                return dx < 0 && absDz <= width / 2 && Math.abs(dx) <= radius * 0.8;
            default:
                return false;
        }
    }

    /**
     * Adds fire/glow effects at volcano peak and rift.
     */
    private void addVolcanoEffects(ChunkManager level, FullChunk chunk, int centerX, int peakY, int centerZ, int riftDirection, NukkitRandom random) {
        // Fire at peak
        for (int i = 0; i < 5; i++) {
            int fx = centerX + random.nextBoundedInt(3) - 1;
            int fz = centerZ + random.nextBoundedInt(3) - 1;
            int fy = peakY + 1 + random.nextBoundedInt(3);
            
            FullChunk targetChunk = getChunkForBlock(level, chunk, fx, fz);
            int localX = fx & 0xF;
            int localZ = fz & 0xF;
            
            if (targetChunk != null && fy < 250 && targetChunk.getBlockId(localX, fy, localZ) == BlockID.AIR) {
                targetChunk.setBlockId(localX, fy, localZ, BlockID.FIRE);
            }
        }

        // Smoke particles (cobwebs as temporary ash)
        for (int i = 0; i < 8; i++) {
            int sx = centerX + random.nextBoundedInt(7) - 3;
            int sz = centerZ + random.nextBoundedInt(7) - 3;
            int sy = peakY + 3 + random.nextBoundedInt(5);
            
            FullChunk targetChunk = getChunkForBlock(level, chunk, sx, sz);
            int localX = sx & 0xF;
            int localZ = sz & 0xF;
            
            if (targetChunk != null && sy < 250 && targetChunk.getBlockId(localX, sy, localZ) == BlockID.AIR) {
                // Use fire as smoke source (no cobweb in Bedrock easily)
                if (random.nextBoundedInt(100) < 30) {
                    targetChunk.setBlockId(localX, sy, localZ, BlockID.FIRE);
                }
            }
        }
    }

    /**
     * Adds ash/gravel ring around volcano base.
     */
    private void addAshRing(ChunkManager level, FullChunk chunk, int centerX, int surfaceY, int centerZ, int radius, NukkitRandom random) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq <= (radius - 2) * (radius - 2) || distSq > radius * radius) {
                    continue;
                }

                int bx = centerX + dx;
                int bz = centerZ + dz;
                
                FullChunk targetChunk = getChunkForBlock(level, chunk, bx, bz);
                int localX = bx & 0xF;
                int localZ = bz & 0xF;
                
                if (targetChunk == null) {
                    continue;
                }

                int topY = targetChunk.getHighestBlockAt(localX, localZ);
                if (topY > 0 && topY < 250) {
                    int blockId = targetChunk.getBlockId(localX, topY, localZ);
                    // Replace grass/dirt with gravel (ash)
                    if (blockId == BlockID.GRASS || blockId == BlockID.DIRT) {
                        targetChunk.setBlockId(localX, topY, localZ, BlockID.GRAVEL);
                    }
                }
            }
        }
    }

    /**
     * Gets the appropriate chunk for a block coordinate that may be in a neighboring chunk.
     */
    private FullChunk getChunkForBlock(ChunkManager level, FullChunk originChunk, int x, int z) {
        int chunkX = originChunk.getX();
        int chunkZ = originChunk.getZ();
        
        if (x < 0) chunkX--;
        else if (x >= 16) chunkX++;
        
        if (z < 0) chunkZ--;
        else if (z >= 16) chunkZ++;
        
        if (chunkX == originChunk.getX() && chunkZ == originChunk.getZ()) {
            return originChunk;
        }
        
        return level.getChunk(chunkX, chunkZ);
    }
}
