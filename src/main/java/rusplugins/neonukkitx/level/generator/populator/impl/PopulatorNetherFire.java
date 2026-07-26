package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;
import it.unimi.dsi.fastutil.ints.IntArrayList;

public class PopulatorNetherFire extends Populator {

    private final int fireBlock;
    private final int replaceBlock;

    public PopulatorNetherFire(int fireBlock, int replaceBlock) {
        this.fireBlock = fireBlock;
        this.replaceBlock = replaceBlock;
    }

    private IntArrayList getHighestWorkableBlocks(ChunkManager level, int x, int z) {
        int y;
        IntArrayList blockYs = new IntArrayList();
        for (y = 127; y > 0; y--) {
            int b = level.getBlockIdAt(x, y, z);
            if ((b == this.replaceBlock) && level.getBlockIdAt(x, y + 1, z) == AIR) {
                blockYs.add(y + 1);
            }
        }
        return blockYs;
    }

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        if (this.replaceBlock == NETHERRACK && random.nextBoundedInt(8) < 7) return;

        int amount = random.nextBoundedInt(4) + 4;

        for (int i = 0; i < amount; ++i) {
            int x = random.nextRange(chunkX << 4, (chunkX << 4) + 15);
            int z = random.nextRange(chunkZ << 4, (chunkZ << 4) + 15);
            IntArrayList ys = getHighestWorkableBlocks(level, x, z);
            for (int y : ys) {
                if (y <= 1) continue;
                level.setBlockAt(x, y, z, this.fireBlock);
            }
        }
    }
}
