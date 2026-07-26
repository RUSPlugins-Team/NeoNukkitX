package rusplugins.neonukkitx.level.biome.impl.extremehills;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockFlower;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.block.BlockSapling;
import rusplugins.neonukkitx.level.biome.type.GrassyBiome;
import rusplugins.neonukkitx.level.generator.object.ore.OreType;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorFlower;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorOre;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorTree;

/**
 * @author DaPorkchop_
 * Nukkit Project
 * <p>
 * make sure this is touching another extreme hills type or it'll look dumb
 *
 * steep mountains with flat areas between
 */
public class ExtremeHillsBiome extends GrassyBiome {
    public ExtremeHillsBiome() {
        this(true);
    }

    public ExtremeHillsBiome(boolean tree) {
        super();

        if (tree) {
            PopulatorTree trees = new PopulatorTree(BlockSapling.SPRUCE);
            trees.setBaseAmount(2);
            trees.setRandomAmount(2);
            this.addPopulator(trees);
        }

        this.addPopulator(new PopulatorOre(STONE, new OreType[]{
                new OreType(Block.get(BlockID.EMERALD_ORE), 11, 1, 0, 32),
                new OreType(Block.get(BlockID.MONSTER_EGG), 7, 9, 0, 63)
        }));

        PopulatorFlower flower = new PopulatorFlower();
        flower.setRandomAmount(3);
        flower.addType(DANDELION, 0);
        flower.addType(RED_FLOWER, BlockFlower.TYPE_POPPY);
        this.addPopulator(flower);

        this.setBaseHeight(1.5f);
        this.setHeightVariation(0.8f);
    }

    @Override
    public String getName() {
        return "Extreme Hills";
    }

    @Override
    public int getCoverId(int x, int y, int z) {
        // Snow on mountain peaks (height > 95)
        if (y > 95) {
            return Block.SNOW_LAYER << Block.DATA_BITS;
        }
        return 0;
    }

    @Override
    public boolean doesOverhang() {
        return true;
    }
}
