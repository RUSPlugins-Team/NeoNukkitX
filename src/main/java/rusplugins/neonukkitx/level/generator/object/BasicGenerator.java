package rusplugins.neonukkitx.level.generator.object;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.math.BlockVector3;
import rusplugins.neonukkitx.math.NukkitRandom;
import rusplugins.neonukkitx.math.Vector3;

public abstract class BasicGenerator {

    //also autism, see below
    public abstract boolean generate(ChunkManager level, NukkitRandom rand, Vector3 position);

    protected void setBlockAndNotifyAdequately(ChunkManager level, BlockVector3 pos, Block state) {
        level.setBlockAt(pos.x, pos.y, pos.z, state.getId(), state.getDamage());
    }

    protected void setBlockAndNotifyAdequately(ChunkManager level, Vector3 pos, Block state) {
        level.setBlockAt((int) pos.x, (int) pos.y, (int) pos.z, state.getId(), state.getDamage());
    }

    //what autism is this? why are we using floating-point vectors for setting block IDs?
    protected void setBlock(ChunkManager level, Vector3 v, Block b) {
        level.setBlockAt((int) v.x, (int) v.y, (int) v.z, b.getId(), b.getDamage());
    }
}