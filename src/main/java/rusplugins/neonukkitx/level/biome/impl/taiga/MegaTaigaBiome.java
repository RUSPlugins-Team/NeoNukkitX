package rusplugins.neonukkitx.level.biome.impl.taiga;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.generator.noise.nukkit.f.SimplexF;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorForestRock;
import rusplugins.neonukkitx.level.generator.populator.impl.PopulatorSmallMushroom;
import rusplugins.neonukkitx.level.generator.populator.impl.tree.SpruceMegaTreePopulator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * @author DaPorkchop_
 * Nukkit Project
 */
public class MegaTaigaBiome extends TaigaBiome {

    private static final SimplexF podzolNoise = new SimplexF(new NukkitRandom(), 2f, 1 / 4f, 1 / 32f);

    public MegaTaigaBiome() {
        super();

        SpruceMegaTreePopulator bigTrees = new SpruceMegaTreePopulator();
        bigTrees.setBaseAmount(6);
        this.addPopulator(bigTrees);

        PopulatorForestRock rock = new PopulatorForestRock();
        rock.setRandomAmount(2);
        this.addPopulator(rock);

        /*PopulatorFlower flower = new PopulatorFlower();
        flower.setRandomAmount(3);
        flower.addType(Block.DANDELION, 0);*/

        PopulatorSmallMushroom smallMushroom = new PopulatorSmallMushroom();
        smallMushroom.setRandomAmount(3);
        this.addPopulator(smallMushroom);

        this.setBaseHeight(0.2f);
        this.setHeightVariation(0.2f);
    }

    @Override
    public String getName() {
        return "Mega Taiga";
    }

    @Override
    public int getSurfaceId(int x, int y, int z) {
        return podzolNoise.noise2D(x, z, true) < 0f ? PODZOL << Block.DATA_BITS : Block.GRASS << Block.DATA_BITS;
    }
}
