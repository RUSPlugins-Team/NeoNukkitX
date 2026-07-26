package rusplugins.neonukkitx.level.biome.impl.nether;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.biome.type.CoveredBiome;
import rusplugins.neonukkitx.level.generator.object.ore.OreType;
import rusplugins.neonukkitx.level.generator.populator.impl.*;

public class SoulSandValleyBiome extends CoveredBiome {

    public SoulSandValleyBiome() {
        this.addPopulator(new PopulatorOre(BlockID.SOUL_SAND, new OreType[]{
                new OreType(Block.get(SOUL_SOIL), 3, 128, 0, 128, SOUL_SAND)
        }));

        this.addPopulator(new PopulatorNetherFire(SOUL_FIRE, SOUL_SOIL));
        this.addPopulator(new PopulatorSoulSandValleyGround());
    }

    @Override
    public String getName() {
        return "Soul Sand Valley";
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return Block.SOUL_SAND << Block.DATA_BITS;
    }

    @Override
    public int getGroundId(int x, int y, int z) {
        return Block.SOUL_SAND << Block.DATA_BITS;
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
