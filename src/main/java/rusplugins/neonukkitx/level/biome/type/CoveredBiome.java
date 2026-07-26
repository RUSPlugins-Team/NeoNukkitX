package rusplugins.neonukkitx.level.biome.type;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;

/**
 * @author DaPorkchop_
 * Nukkit Project
 * <p>
 * A biome with ground covering
 * </p>
 */
public abstract class CoveredBiome extends Biome {

    public int getCoverId(int x, int z) {
        return 0;
    }

    public int getCoverId(int x, int y, int z) {
        return this.getCoverId(x, z);
    }

    public int getSurfaceDepth(int x, int y, int z) {
        return 1;
    }

    public abstract int getSurfaceId(int x, int y, int z);

    public int getGroundDepth(int x, int y, int z) {
        return 4;
    }

    public abstract int getGroundId(int x, int y, int z);

    public void doCover(int x, int z, FullChunk chunk) {
        final int fullX = (chunk.getX() << 4) | x;
        final int fullZ = (chunk.getZ() << 4) | z;

        boolean hasCovered = false;
        int realY;
        for (int y = 254; y > 32; y--) {
            int blockId = chunk.getBlockId(x, y, z);
            
            // Skip water blocks entirely — don't place anything in water columns
            if (blockId == BlockID.WATER || blockId == BlockID.STILL_WATER) {
                hasCovered = false;
                continue;
            }
            
            if (chunk.getFullBlock(x, y, z) == (STONE << Block.DATA_BITS)) {
                COVER:
                if (!hasCovered) {
                    // Check if this stone is underwater (water block above it)
                    int aboveId = chunk.getBlockId(x, y + 1, z);
                    boolean isUnderwater = (aboveId == BlockID.WATER || aboveId == BlockID.STILL_WATER || y < Normal.seaHeight);
                    
                    if (y >= Normal.seaHeight && !isUnderwater) {
                        // Above water: place cover block (snow, etc.) and surface (grass)
                        final int coverBlock = this.getCoverId(fullX, y, fullZ);
                        chunk.setFullBlockId(x, y + 1, z, coverBlock);
                        int surfaceDepth = this.getSurfaceDepth(fullX, y, fullZ);
                        for (int i = 0; i < surfaceDepth; i++) {
                            realY = y - i;
                            if (chunk.getFullBlock(x, realY, z) == (STONE << Block.DATA_BITS)) {
                                chunk.setFullBlockId(x, realY, z, this.getSurfaceId(fullX, realY, fullZ));
                            } else break COVER;
                        }
                        y -= surfaceDepth;
                    }
                    
                    // Ground layer (dirt) — applies both above and below water
                    int groundDepth = this.getGroundDepth(fullX, y, fullZ);
                    for (int i = 0; i < groundDepth; i++) {
                        realY = y - i;
                        if (chunk.getFullBlock(x, realY, z) == (STONE << Block.DATA_BITS)) {
                            chunk.setFullBlockId(x, realY, z, this.getGroundId(fullX, realY, fullZ));
                        } else break COVER;
                    }
                    y -= groundDepth - 1;
                }
                hasCovered = true;
            } else {
                if (hasCovered) {
                    hasCovered = false;
                }
            }
        }
    }
}
