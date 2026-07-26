package rusplugins.neonukkitx.level.biome.impl.savanna;

import rusplugins.neonukkitx.block.BlockFlower;
import rusplugins.neonukkitx.block.BlockSapling;
import rusplugins.neonukkitx.level.biome.type.GrassyBiome;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorFlower;
import rusplugins.neonukkitx.level.generator.populator.impl.tree.SavannaTreePopulator;

/**
 * @author DaPorkchop_
 */
public class SavannaBiome extends GrassyBiome {

    public SavannaBiome() {
        super();

        SavannaTreePopulator tree = new SavannaTreePopulator(BlockSapling.ACACIA);
        tree.setBaseAmount(1);
        this.addPopulator(tree);

        PopulatorFlower flower = new PopulatorFlower();
        flower.setRandomAmount(3);
        flower.addType(DANDELION, 0);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_POPPY);
        this.addPopulator(flower);

        this.setBaseHeight(0.125f);
        this.setHeightVariation(0.05f);
    }

    @Override
    public String getName() {
        return "Savanna";
    }

    @Override
    public boolean canRain() {
        return false;
    }
}
