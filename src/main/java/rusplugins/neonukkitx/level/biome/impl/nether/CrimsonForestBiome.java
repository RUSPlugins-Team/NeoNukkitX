package rusplugins.neonukkitx.level.biome.impl.nether;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.biome.type.CoveredBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorCrimsonFungus;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorCrimsonForestGround;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorWeepingVines;

public class CrimsonForestBiome extends CoveredBiome {

    public CrimsonForestBiome() {
        this.addPopulator(new PopulatorCrimsonFungus());
        this.addPopulator(new PopulatorCrimsonForestGround());
        this.addPopulator(new PopulatorWeepingVines());
    }

    @Override
    public String getName() {
        return "Crimson Forest";
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return Block.CRIMSON_NYLIUM << Block.DATA_BITS;
    }

    @Override
    public int getGroundId(int x, int y, int z) {
        return Block.NETHERRACK << Block.DATA_BITS;
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
