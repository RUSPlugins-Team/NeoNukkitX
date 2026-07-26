package rusplugins.neonukkitx.level.biome.impl.beach;

import rusplugins.neonukkitx.level.biome.type.SandyBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorSugarcane;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorTallSugarcane;

/**
 * @author PeratX
 * Nukkit Project
 */
public class BeachBiome extends SandyBiome {
    public BeachBiome() {
        PopulatorSugarcane sugarcane = new PopulatorSugarcane();
        sugarcane.setRandomAmount(3);
        this.addPopulator(sugarcane);

        PopulatorTallSugarcane tallSugarcane = new PopulatorTallSugarcane();
        tallSugarcane.setRandomAmount(1);
        this.addPopulator(tallSugarcane);

        this.setBaseHeight(0f);
        this.setHeightVariation(0.025f);
    }

    @Override
    public String getName() {
        return "Beach";
    }
}
