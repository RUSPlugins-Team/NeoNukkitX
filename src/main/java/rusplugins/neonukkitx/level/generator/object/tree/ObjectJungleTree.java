package rusplugins.neonukkitx.level.generator.object.tree;

import rusplugins.neonukkitx.block.BlockWood;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ObjectJungleTree extends ObjectTree {
    private int treeHeight = 8;

    @Override
    public int getType() {
        return BlockWood.JUNGLE;
    }

    @Override
    public int getTreeHeight() {
        return this.treeHeight;
    }

    @Override
    public void placeObject(ChunkManager level, int x, int y, int z, NukkitRandom random) {
        this.treeHeight = random.nextBoundedInt(6) + 4;
        super.placeObject(level, x, y, z, random);
    }
}
