package rusplugins.neonukkitx.level.biome.impl.desert;

import rusplugins.neonukkitx.level.biome.type.SandyBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorCactus;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorDeadBush;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class DesertBiome extends SandyBiome {
    public DesertBiome() {
        PopulatorCactus cactus = new PopulatorCactus();
        cactus.setBaseAmount(2);
        this.addPopulator(cactus);

        PopulatorDeadBush deadbush = new PopulatorDeadBush();
        deadbush.setBaseAmount(2);
        this.addPopulator(deadbush);

        this.setBaseHeight(0.125f);
        this.setHeightVariation(0.05f);
    }

    @Override
    public String getName() {
        return "Desert";
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
