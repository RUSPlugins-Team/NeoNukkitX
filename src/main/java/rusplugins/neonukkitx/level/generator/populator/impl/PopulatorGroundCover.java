package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.type.CoveredBiome;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * @author DaPorkchop_
 * Nukkit Project
 */
public class PopulatorGroundCover extends Populator {

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        for (int x = 15; x >= 0; x--) {
            for (int z = 15; z >= 0; z--) {
                Biome realBiome = Biome.getBiome(chunk.getBiomeId(x, z));
                if (realBiome instanceof CoveredBiome) {
                    ((CoveredBiome) realBiome).doCover(x, z, chunk);
                }
            }
        }
    }
}