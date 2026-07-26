package rusplugins.neonukkitx.level.generator.populator.helper;

import rusplugins.neonukkitx.level.format.FullChunk;

import static rusplugins.neonukkitx.block.BlockID.*;

/**
 * @author DaPorkchop_
 */
public interface EnsureGrassBelow {

    static boolean ensureGrassBelow(int x, int y, int z, FullChunk chunk) {
        int bid = chunk.getBlockId(x, y - 1, z);
        return bid == GRASS || bid == PODZOL;
    }

    static boolean ensureGrassOrSandBelow(int x, int y, int z, FullChunk chunk) {
        int bid = chunk.getBlockId(x, y - 1, z);
        return bid == GRASS || bid == PODZOL || bid == SAND;
    }
}
