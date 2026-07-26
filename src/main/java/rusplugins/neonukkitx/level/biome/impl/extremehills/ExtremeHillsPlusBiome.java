package rusplugins.neonukkitx.level.biome.impl.extremehills;

/**
 * @author DaPorkchop_
 * Nukkit Project
 * <p>
 * make sure this is touching another extreme hills type or it'll look dumb
 *
 * vertical cliffs, flat on top and on bottom
 */
public class ExtremeHillsPlusBiome extends ExtremeHillsBiome {

    public ExtremeHillsPlusBiome() {
        this(true);
    }

    public ExtremeHillsPlusBiome(boolean tree) {
        super(tree);

        this.setBaseHeight(2.0f);
        this.setHeightVariation(1.0f);
    }

    @Override
    public String getName() {
        return "Extreme Hills+";
    }
}
