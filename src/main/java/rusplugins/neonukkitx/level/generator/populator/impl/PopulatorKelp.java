package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockLayer;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.format.leveldb.structure.LevelDBChunk;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureBelow;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureCover;
import rusplugins.neonukkitx.level.generator.populator.type.PopulatorOceanFloorSurfaceBlock;
import rusplugins.neonukkitx.math.NukkitRandom;
import rusplugins.neonukkitx.utils.Utils;

public class PopulatorKelp extends PopulatorOceanFloorSurfaceBlock {

    @Override
    protected boolean canStay(int x, int y, int z, FullChunk chunk) {
        if (chunk instanceof rusplugins.neonukkitx.level.format.anvil.Chunk) return false;
        return EnsureCover.ensureWaterCover(x, y, z, chunk) && EnsureBelow.ensureBelow(x, y, z, GRAVEL, chunk);
    }

    @Override
    protected int getBlockId(int x, int z, NukkitRandom random, FullChunk chunk) {
        return Block.BLOCK_KELP << Block.DATA_BITS;
    }

    @Override
    protected void placeBlock(int x, int y, int z, int id, FullChunk chunk, NukkitRandom random) {
        int height = Utils.rand(1, 25);
        int lastTop = y;

        boolean leveldb = chunk instanceof LevelDBChunk;

        for (int part = 0; part < height; part++) {
            int yy = y + part;
            if (yy < 256 && chunk.getBlockId(x, yy, z) == STILL_WATER && chunk.getBlockId(x, yy + 1, z) == STILL_WATER) {
                chunk.setBlock(x, yy, z, Block.BLOCK_KELP, leveldb ? part : (int) (part / 1.6));
                chunk.setFullBlockId(x, yy, z, BlockLayer.WATERLOGGED, Block.STILL_WATER << Block.DATA_BITS);
                lastTop = yy;
            } else {
                lastTop = yy - 1;
                break;
            }
        }

        // Stop top part growing
        chunk.setBlockData(x, lastTop, z, leveldb ? 24 : 15);
    }
}
