package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureBelow;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureCover;
import rusplugins.neonukkitx.level.generator.populator.type.PopulatorSurfaceBlock;
import rusplugins.neonukkitx.math.NukkitRandom;

import java.util.concurrent.ThreadLocalRandom;

public class PopulatorSweetBerryBush extends PopulatorSurfaceBlock {

    @Override
    protected boolean canStay(int x, int y, int z, FullChunk chunk) {
        if (chunk instanceof rusplugins.neonukkitx.level.format.anvil.Chunk) return false;
        return EnsureCover.ensureCover(x, y, z, chunk) && EnsureBelow.ensureBelow(x, y, z, GRASS, chunk);
    }

    @Override
    protected int getBlockId(int x, int z, NukkitRandom random, FullChunk chunk) {
        return (BlockID.SWEET_BERRY_BUSH << Block.DATA_BITS) + ThreadLocalRandom.current().nextInt(3);
    }
}
