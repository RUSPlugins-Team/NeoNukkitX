package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureCover;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureGrassBelow;
import rusplugins.neonukkitx.level.generator.populator.type.PopulatorSurfaceBlock;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * @author DaPorkchop_
 */
public class PopulatorMelon extends PopulatorSurfaceBlock {

    @Override
    protected boolean canStay(int x, int y, int z, FullChunk chunk) {
        return EnsureCover.ensureCover(x, y, z, chunk) && EnsureGrassBelow.ensureGrassBelow(x, y, z, chunk);
    }

    @Override
    protected int getBlockId(int x, int z, NukkitRandom random, FullChunk chunk) {
        return Block.MELON_BLOCK << Block.DATA_BITS;
    }
}
