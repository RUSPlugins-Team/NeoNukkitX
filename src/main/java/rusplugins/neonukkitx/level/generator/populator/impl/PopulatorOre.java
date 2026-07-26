package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.object.ore.OreType;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitMath;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * @author DaPorkchop_
 */
public class PopulatorOre extends Populator {

    private final int replaceId;
    private final OreType[] oreTypes;

    public PopulatorOre(int replaceId, OreType[] oreTypes) {
        this.replaceId = replaceId;
        this.oreTypes = oreTypes;
    }

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        int sx = chunkX << 4;
        int ex = sx + 15;
        int sz = chunkZ << 4;
        int ez = sz + 15;
        for (OreType type : this.oreTypes) {
            for (int i = 0; i < type.clusterCount; i++) {
                int x = NukkitMath.randomRange(random, sx, ex);
                int z = NukkitMath.randomRange(random, sz, ez);
                int y = NukkitMath.randomRange(random, type.minHeight, type.maxHeight);
                if (level.getBlockIdAt(x, y, z) != replaceId) {
                    continue;
                }
                if (type.clusterSize == 1) {
                    level.setBlockFullIdAt(x, y, z, type.fullId);
                } else {
                    type.spawn(level, random, replaceId, x, y, z);
                }
            }
        }
    }
}
