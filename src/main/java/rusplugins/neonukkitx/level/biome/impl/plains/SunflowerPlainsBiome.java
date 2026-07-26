package rusplugins.neonukkitx.level.biome.impl.plains;

import rusplugins.neonukkitx.block.BlockDoublePlant;
import rusplugins.neonukkitx.block.BlockFlower;
import rusplugins.neonukkitx.block.BlockSapling;
import rusplugins.neonukkitx.level.biome.type.GrassyBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorDoublePlant;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorFlower;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorTree;

/**
 * @author DaPorkchop_
 * Nukkit Project
 */
public class SunflowerPlainsBiome extends GrassyBiome {

    public SunflowerPlainsBiome() {
        super();

        PopulatorTree trees = new PopulatorTree(BlockSapling.OAK);
        trees.setRandomAmount(1);
        this.addPopulator(trees);

        PopulatorDoublePlant sunflower = new PopulatorDoublePlant(BlockDoublePlant.SUNFLOWER);
        sunflower.setBaseAmount(8);
        sunflower.setRandomAmount(5);
        this.addPopulator(sunflower);

        PopulatorFlower flower = new PopulatorFlower();
        flower.setRandomAmount(3);
        flower.addType(DANDELION, 0);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_POPPY);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_AZURE_BLUET);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_RED_TULIP);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_ORANGE_TULIP);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_WHITE_TULIP);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_PINK_TULIP);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_OXEYE_DAISY);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_CORNFLOWER);
        flower.addType(DOUBLE_PLANT, BlockDoublePlant.LILAC);
        this.addPopulator(flower);

        this.setBaseHeight(0.125f);
        this.setHeightVariation(0.05f);
    }

    @Override
    public String getName() {
        return "Sunflower Plains";
    }
}
