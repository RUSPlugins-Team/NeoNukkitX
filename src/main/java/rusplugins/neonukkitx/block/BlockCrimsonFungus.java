package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.level.generator.object.tree.ObjectCrimsonTree;
import rusplugins.neonukkitx.level.generator.object.tree.ObjectNetherTree;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockCrimsonFungus extends BlockFungus {

    public BlockCrimsonFungus() {
    }

    @Override
    public int getId() {
        return CRIMSON_FUNGUS;
    }

    @Override
    public String getName() {
        return "Crimson Fungus";
    }

    @Override
    protected ObjectNetherTree getTree() {
        return new ObjectCrimsonTree();
    }

    @Override
    protected int getGround() {
        return CRIMSON_NYLIUM;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.NETHERRACK_BLOCK_COLOR;
    }
}
