package rusplugins.neonukkitx.level.generator.populator.type;

import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.helper.PopulatorHelpers;

public abstract class PopulatorOceanFloorSurfaceBlock extends PopulatorSurfaceBlock {

    @Override
    protected int getHighestWorkableBlock(ChunkManager level, int x, int z, FullChunk chunk) {
        int y;
        for (y = Normal.seaHeight - 1; y >= 0; --y) {
            if (!PopulatorHelpers.isNonOceanSolid(chunk.getBlockId(x, y, z))) {
                break;
            }
        }
        return y == 0 ? -1 : ++y;
    }
}
