package rusplugins.neonukkitx.level.biome.impl.roofedforest;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockFlower;
import rusplugins.neonukkitx.level.biome.type.GrassyBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.MushroomPopulator;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorFlower;
import rusplugins.neonukkitx.level.generator.populator.impl.tree.DarkOakTreePopulator;

public class RoofedForestBiome extends GrassyBiome {

    public RoofedForestBiome() {
        super();

        DarkOakTreePopulator tree = new DarkOakTreePopulator();
        tree.setBaseAmount(24);
        tree.setRandomAmount(24);
        this.addPopulator(tree);

        PopulatorFlower flower = new PopulatorFlower();
        flower.setBaseAmount(3);
        flower.addType(Block.DANDELION, 0);
        flower.addType(Block.RED_FLOWER, BlockFlower.TYPE_POPPY);

        MushroomPopulator mushroom = new MushroomPopulator();
        mushroom.setBaseAmount(1);
        mushroom.setRandomAmount(2);
        this.addPopulator(mushroom);
    }

    @Override
    public String getName() {
        return "Roofed Forest";
    }
}
