package rusplugins.neonukkitx.level.biome.impl.ocean;

import rusplugins.neonukkitx.block.Block;

/**
 * @author DaPorkchop_
 * Nukkit Project
 * <p>
 * This biome does not generate naturally
 */
public class FrozenOceanBiome extends OceanBiome {
    public FrozenOceanBiome() {
        super();
    }

    @Override
    public String getName() {
        return "Frozen Ocean";
    }

    @Override
    public boolean isFreezing() {
        return true;
    }

    @Override
    public boolean canRain() {
        return false;
    }

    @Override
    public int getCoverId(int x, int z) {
        return SNOW_LAYER << Block.DATA_BITS;
    }
}
