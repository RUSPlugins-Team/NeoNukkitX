package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.EnumBiome;
import rusplugins.neonukkitx.level.biome.impl.beach.BeachBiome;
import rusplugins.neonukkitx.level.biome.impl.mushroom.MushroomIslandBiome;
import rusplugins.neonukkitx.level.biome.impl.swamp.SwampBiome;
import rusplugins.neonukkitx.level.biome.type.WateryBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Populates beaches by replacing grass/dirt with sand near water bodies.
 */
public class PopulatorBeach extends Populator {

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome biome = Biome.getBiome(chunk.getBiomeId(x, z));
                
                // Skip water biomes, mushroom islands and existing beaches
                if (biome instanceof WateryBiome || biome instanceof MushroomIslandBiome || biome instanceof BeachBiome) {
                    continue;
                }

                int highestY = chunk.getHighestBlockAt(x, z);
                
                // Only process blocks above sea level
                if (highestY < Normal.seaHeight - 2 || highestY > Normal.seaHeight + 4) {
                    continue;
                }

                // Check if there's water nearby (within 3 blocks)
                if (hasWaterNearby(chunk, x, z, 3)) {
                    // Replace surface blocks with sand
                    for (int y = highestY; y >= highestY - 3 && y > 0; y--) {
                        int blockId = chunk.getBlockId(x, y, z);
                        if (blockId == BlockID.GRASS || blockId == BlockID.DIRT) {
                            chunk.setBlockId(x, y, z, BlockID.SAND);
                        } else if (blockId == BlockID.STONE) {
                            break; // Stop at stone
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if there's water within the specified radius.
     */
    private boolean hasWaterNearby(FullChunk chunk, int x, int z, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int nx = x + dx;
                int nz = z + dz;
                
                // Check bounds within chunk
                if (nx < 0 || nx >= 16 || nz < 0 || nz >= 16) {
                    continue;
                }
                
                int highestY = chunk.getHighestBlockAt(nx, nz);
                if (highestY <= Normal.seaHeight) {
                    // Check if the highest block is water
                    int blockId = chunk.getBlockId(nx, highestY, nz);
                    if (blockId == BlockID.WATER || blockId == BlockID.STILL_WATER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
