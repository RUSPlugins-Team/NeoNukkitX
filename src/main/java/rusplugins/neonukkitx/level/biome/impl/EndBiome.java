package rusplugins.neonukkitx.level.biome.impl;

import rusplugins.neonukkitx.level.biome.Biome;

public class EndBiome extends Biome {

    public EndBiome() {

    }

    @Override
    public String getName() {
        return "The End";
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
