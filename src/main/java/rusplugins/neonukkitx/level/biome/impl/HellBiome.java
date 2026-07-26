package rusplugins.neonukkitx.level.biome.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.biome.type.CoveredBiome;

public class HellBiome extends CoveredBiome {

    public HellBiome() {

    }

    @Override
    public String getName() {
        return "Hell";
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return Block.NETHERRACK << Block.DATA_BITS;
    }

    @Override
    public int getGroundId(int x, int y, int z) {
        return Block.NETHERRACK << Block.DATA_BITS;
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
