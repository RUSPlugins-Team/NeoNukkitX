package rusplugins.neonukkitx.level.biome.impl.iceplains;

import rusplugins.neonukkitx.block.BlockSapling;
import rusplugins.neonukkitx.level.biome.type.SnowyBiome;
import rusplugins.neonukkitx.level.generator.object.tree.ObjectTree;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorTree;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class IcePlainsBiome extends SnowyBiome {

    public IcePlainsBiome() {
        super();

        PopulatorTree trees = new PopulatorTree(BlockSapling.SPRUCE + ObjectTree.SNOWY_TREE);
        trees.setRandomAmount(1);
        this.addPopulator(trees);

        this.setBaseHeight(0.125f);
        this.setHeightVariation(0.05f);
    }

    public String getName() {
        return "Ice Plains";
    }
}
