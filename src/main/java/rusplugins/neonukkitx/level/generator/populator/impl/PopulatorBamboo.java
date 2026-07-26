package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureCover;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureGrassBelow;
import rusplugins.neonukkitx.level.generator.populator.type.PopulatorSurfaceBlock;
import rusplugins.neonukkitx.math.NukkitRandom;
import rusplugins.neonukkitx.utils.Utils;

public class PopulatorBamboo extends PopulatorSurfaceBlock {

    @Override
    protected boolean canStay(int x, int y, int z, FullChunk chunk) {
        if (chunk instanceof rusplugins.neonukkitx.level.format.anvil.Chunk) return false;
        return EnsureCover.ensureCover(x, y, z, chunk) && EnsureGrassBelow.ensureGrassBelow(x, y, z, chunk);
    }

    @Override
    protected int getBlockId(int x, int z, NukkitRandom random, FullChunk chunk) {
        return Block.BAMBOO << Block.DATA_BITS;
    }

    @Override
    protected void placeBlock(int x, int y, int z, int id, FullChunk chunk, NukkitRandom random) {
        int height = Utils.rand(5, 16);
        int part = 0;
        while (part < height) {
            int yy = y + part;
            if (yy < 256 && chunk.getBlockId(x, yy, z) == AIR) {
                super.placeBlock(x, yy, z, id, chunk, random);
            } else {
                break;
            }
            part++;
        }
        // Top part
        int yy = y + part;
        if (yy < 256 && chunk.getBlockId(x, yy, z) == AIR) {
            chunk.setBlock(x, yy, z, BAMBOO, 2);
        } else {
            chunk.setBlock(x, yy - 1, z, BAMBOO, 2);
        }
    }
}
