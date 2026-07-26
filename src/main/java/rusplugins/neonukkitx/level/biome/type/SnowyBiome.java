package rusplugins.neonukkitx.level.biome.type;

import rusplugins.neonukkitx.block.Block;

/**
 * @author DaPorkchop_
 * Nukkit Project
 */
public abstract class SnowyBiome extends GrassyBiome {
    public SnowyBiome() {
        super();
    }

    @Override
    public int getCoverId(int x, int y, int z) {
        return Block.SNOW_LAYER << Block.DATA_BITS;
    }

    @Override
    public boolean isFreezing() {
        return true;
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
