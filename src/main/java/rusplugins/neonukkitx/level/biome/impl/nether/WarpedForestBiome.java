package rusplugins.neonukkitx.level.biome.impl.nether;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.biome.type.CoveredBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorTwistingVines;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorWarpedFungus;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorWarpedForestGround;

public class WarpedForestBiome extends CoveredBiome {

    public WarpedForestBiome() {
        this.addPopulator(new PopulatorWarpedFungus());
        this.addPopulator(new PopulatorWarpedForestGround());
        this.addPopulator(new PopulatorTwistingVines());
    }

    @Override
    public String getName() {
        return "Warped Forest";
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return Block.WARPED_NYLIUM << Block.DATA_BITS;
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
