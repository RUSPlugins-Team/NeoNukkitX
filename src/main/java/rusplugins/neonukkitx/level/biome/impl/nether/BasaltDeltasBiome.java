package rusplugins.neonukkitx.level.biome.impl.nether;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.biome.type.CoveredBiome;
import rusplugins.neonukkitx.level.generator.object.ore.OreType;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorBasaltDeltaLava;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorBasaltDeltaMagma;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorBasaltDeltaPillar;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorOre;

public class BasaltDeltasBiome extends CoveredBiome {

    public BasaltDeltasBiome() {
        this.addPopulator(new PopulatorOre(BlockID.BASALT, new OreType[]{
                new OreType(Block.get(BlockID.BLACKSTONE), 4, 128, 0, 128, BASALT)
        }));

        this.addPopulator(new PopulatorBasaltDeltaLava());
        this.addPopulator(new PopulatorBasaltDeltaMagma());
        this.addPopulator(new PopulatorBasaltDeltaPillar());
    }

    @Override
    public String getName() {
        return "Basalt Deltas";
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return Block.BASALT << Block.DATA_BITS;
    }

    @Override
    public int getGroundId(int x, int y, int z) {
        return Block.BASALT << Block.DATA_BITS;
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
