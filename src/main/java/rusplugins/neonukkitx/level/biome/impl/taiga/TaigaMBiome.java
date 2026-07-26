package rusplugins.neonukkitx.level.biome.impl.taiga;

/**
 * @author DaPorkchop_
 * Nukkit Project
 */
public class TaigaMBiome extends TaigaBiome {
    public TaigaMBiome() {
        super();

        this.setBaseHeight(0.3f);
        this.setHeightVariation(0.4f);
    }

    @Override
    public String getName() {
        return "Taiga M";
    }
}
