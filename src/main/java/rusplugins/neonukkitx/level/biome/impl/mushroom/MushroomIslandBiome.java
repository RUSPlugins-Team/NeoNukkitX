package rusplugins.neonukkitx.level.biome.impl.mushroom;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.biome.type.GrassyBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.MushroomPopulator;

public class MushroomIslandBiome extends GrassyBiome {
    public MushroomIslandBiome() {
        MushroomPopulator mushroomPopulator = new MushroomPopulator();
        mushroomPopulator.setBaseAmount(1);
        addPopulator(mushroomPopulator);

        this.setBaseHeight(0.2f);
        this.setHeightVariation(0.3f);
    }

    @Override
    public String getName() {
        return "Mushroom Island";
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return Block.MYCELIUM << Block.DATA_BITS;
    }
}
