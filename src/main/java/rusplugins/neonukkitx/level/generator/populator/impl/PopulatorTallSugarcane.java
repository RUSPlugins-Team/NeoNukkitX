package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.math.NukkitRandom;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Niall Lindsay (Niall7459)
 * <p>
 * Nukkit Project
 * </p>
 */

public class PopulatorTallSugarcane extends PopulatorSugarcane {
    @Override
    protected void placeBlock(int x, int y, int z, int id, FullChunk chunk, NukkitRandom random) {
        int height = ThreadLocalRandom.current().nextInt(3) + 1;
        if (y + height > 255) return;
        for (int i = 0; i < height; i++)    {
            chunk.setFullBlockId(x, y + i, z, id);
        }
    }
}
