package rusplugins.neonukkitx.level.biome.type;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockDoublePlant;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorDoublePlant;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorGrass;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class GrassyBiome extends CoveredBiome {

    public GrassyBiome() {
        PopulatorGrass grass = new PopulatorGrass();
        grass.setBaseAmount(30);
        this.addPopulator(grass);

        PopulatorDoublePlant tallGrass = new PopulatorDoublePlant(BlockDoublePlant.TALL_GRASS);
        tallGrass.setBaseAmount(5);
        this.addPopulator(tallGrass);
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return Block.GRASS << Block.DATA_BITS;
    }

    @Override
    public int getGroundId(int x, int y, int z) {
        return Block.DIRT << Block.DATA_BITS;
    }
}
