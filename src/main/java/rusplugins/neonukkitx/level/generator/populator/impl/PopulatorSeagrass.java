package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockLayer;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.populator.helper.EnsureCover;
import rusplugins.neonukkitx.level.generator.populator.type.PopulatorOceanFloorSurfaceBlock;
import rusplugins.neonukkitx.math.NukkitRandom;

public class PopulatorSeagrass extends PopulatorOceanFloorSurfaceBlock {

    @Override
    protected boolean canStay(int x, int y, int z, FullChunk chunk) {
        if (chunk instanceof rusplugins.neonukkitx.level.format.anvil.Chunk) return false;
        int down;
        return EnsureCover.ensureWaterCover(x, y, z, chunk) && ((down = chunk.getBlockId(x, y - 1, z)) == DIRT || down == SAND || down == GRAVEL);
    }

    @Override
    protected int getBlockId(int x, int z, NukkitRandom random, FullChunk chunk) {
        return Block.SEAGRASS << Block.DATA_BITS;
    }

    @Override
    protected void placeBlock(int x, int y, int z, int id, FullChunk chunk, NukkitRandom random) {
        if (y < 255 && random.nextDouble() < 0.3 && chunk.getBlockId(x, y + 1, z) == STILL_WATER) {
            chunk.setBlock(x, y, z, SEAGRASS, 2); // tall seagrass bottom
            chunk.setBlock(x, y + 1, z, SEAGRASS, 1); // tall seagrass top
            chunk.setFullBlockId(x, y, z, BlockLayer.WATERLOGGED, Block.STILL_WATER << Block.DATA_BITS);
            chunk.setFullBlockId(x, y + 1, z, BlockLayer.WATERLOGGED, Block.STILL_WATER << Block.DATA_BITS);
        } else {
            chunk.setFullBlockId(x, y, z, id);
            chunk.setFullBlockId(x, y, z, BlockLayer.WATERLOGGED, Block.STILL_WATER << Block.DATA_BITS);
        }
    }
}
