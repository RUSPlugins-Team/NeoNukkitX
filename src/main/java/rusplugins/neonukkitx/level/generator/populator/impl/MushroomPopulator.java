package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.object.mushroom.BigMushroom;
import rusplugins.neonukkitx.level.generator.populator.type.PopulatorCount;
import rusplugins.neonukkitx.math.NukkitRandom;
import rusplugins.neonukkitx.math.Vector3;

/**
 * @author DaPorkchop_
 */
public class MushroomPopulator extends PopulatorCount {

    private final int type;

    public MushroomPopulator() {
        this(-1);
    }

    public MushroomPopulator(int type) {
        this.type = type;
    }

    @Override
    public void populateCount(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        int x = (chunkX << 4) | random.nextBoundedInt(16);
        int z = (chunkZ << 4) | random.nextBoundedInt(16);
        int y = this.getHighestWorkableBlock(level, x, z, chunk);
        if (y != -1) {
            new BigMushroom(type).generate(level, random, new Vector3(x, y, z));
        }
    }

    @Override
    protected int getHighestWorkableBlock(ChunkManager level, int x, int z, FullChunk chunk) {
        int y;
        x &= 0xF;
        z &= 0xF;
        for (y = 254; y > 0; --y) {
            int b = chunk.getBlockId(x, y, z);
            if (b == Block.DIRT || b == Block.GRASS || b == Block.MYCELIUM) {
                break;
            } else if (b != Block.AIR && b != Block.SNOW_LAYER) {
                return -1;
            }
        }

        return ++y;
    }
}