package rusplugins.neonukkitx.utils;

import rusplugins.neonukkitx.math.BlockFace;

/**
 * Interface of a faceable Block
 */
public interface Faceable {

    /**
     * Get BlockFace of the direction the block is facing
     *
     * @return BlockFace of the direction the block is facing
     */
    BlockFace getBlockFace();
}