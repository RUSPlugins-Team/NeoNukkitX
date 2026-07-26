package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.utils.BlockColor;

/**
 * Created on 2015/11/23 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockFenceGateAcacia extends BlockFenceGate {

    public BlockFenceGateAcacia() {
        this(0);
    }

    public BlockFenceGateAcacia(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return FENCE_GATE_ACACIA;
    }

    @Override
    public String getName() {
        return "Acacia Fence Gate";
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.ORANGE_BLOCK_COLOR;
    }
}
