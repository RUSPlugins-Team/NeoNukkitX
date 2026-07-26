package rusplugins.neonukkitx.level.biome.impl.ocean;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorSeagrass;

public class WarmOceanBiome extends OceanBiome {

    public WarmOceanBiome() {
        PopulatorSeagrass populatorSeagrass = new PopulatorSeagrass();
        populatorSeagrass.setBaseAmount(24);
        populatorSeagrass.setRandomAmount(24);
        this.addPopulator(populatorSeagrass);

        this.setBaseHeight(-1.0f);
        this.setHeightVariation(0.1f);
    }

    @Override
    public String getName() {
        return "Warm Ocean";
    }

    @Override
    public int getGroundId(int x, int y, int z) {
        return Block.SAND << Block.DATA_BITS;
    }
}
