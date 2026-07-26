package rusplugins.neonukkitx.level.biome.impl.beach;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.biome.type.SandyBiome;

public class ColdBeachBiome extends SandyBiome {
    public ColdBeachBiome() {

        this.setBaseHeight(0f);
        this.setHeightVariation(0.025f);
    }

    @Override
    public int getCoverId(int x, int z) {
        return Block.SNOW_LAYER << Block.DATA_BITS;
    }

    @Override
    public String getName() {
        return "Cold Beach";
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
