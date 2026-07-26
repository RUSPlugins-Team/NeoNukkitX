package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

public class WaterIcePopulator extends Populator {

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome biome = Biome.getBiome(chunk.getBiomeId(x, z));
                if (biome.isFreezing()) {
                    int topBlock = chunk.getHighestBlockAt(x, z);
                    if (chunk.getBlockId(x, topBlock, z) == STILL_WATER) {
                        chunk.setBlockId(x, topBlock, z, ICE);
                    }
                }
            }
        }
    }
}
