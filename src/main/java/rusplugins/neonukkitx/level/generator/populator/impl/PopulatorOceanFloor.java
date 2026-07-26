package rusplugins.neonukkitx.level.generator.populator.impl;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.level.biome.Biome;
import rusplugins.neonukkitx.level.biome.EnumBiome;
import rusplugins.neonukkitx.level.biome.type.WateryBiome;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.generator.Normal;
import rusplugins.neonukkitx.level.generator.populator.type.Populator;
import rusplugins.neonukkitx.math.NukkitRandom;

/**
 * Improves ocean floors with proper sand, gravel, clay, and depth variation.
 * Different ocean types have different floor blocks.
 */
public class PopulatorOceanFloor extends Populator {

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        Biome centerBiome = Biome.getBiome(chunk.getBiomeId(7, 7));
        if (!(centerBiome instanceof WateryBiome)) {
            return;
        }

        int biomeId = chunk.getBiomeId(7, 7);

        // Determine floor type based on ocean temperature
        FloorType floorType = getFloorType(biomeId);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // Must be underwater
                int surfaceY = chunk.getHighestBlockAt(x, z);
                if (surfaceY > Normal.seaHeight) {
                    continue;
                }

                // Find ocean floor
                int floorY = -1;
                for (int y = Normal.seaHeight; y > 5; y--) {
                    int blockId = chunk.getBlockId(x, y, z);
                    if (blockId == BlockID.STONE || blockId == BlockID.DIRT || blockId == BlockID.GRAVEL) {
                        floorY = y;
                        break;
                    }
                }

                if (floorY < 0) continue;

                // Replace floor with appropriate blocks
                replaceFloor(chunk, x, floorY, z, floorType, random);

                // Add depth variation — underwater hills and valleys
                addDepthVariation(chunk, x, floorY, z, floorType, random);

                // Frozen ocean: ice on surface
                if (floorType == FloorType.FROZEN) {
                    addIce(chunk, x, surfaceY, z, random);
                }
            }
        }
    }

    private FloorType getFloorType(int biomeId) {
        if (biomeId == EnumBiome.WARM_OCEAN.id || biomeId == EnumBiome.DEEP_WARM_OCEAN.id) {
            return FloorType.WARM;
        } else if (biomeId == EnumBiome.LUKEWARM_OCEAN.id || biomeId == EnumBiome.DEEP_LUKEWARM_OCEAN.id) {
            return FloorType.LUKEWARM;
        } else if (biomeId == EnumBiome.COLD_OCEAN.id || biomeId == EnumBiome.DEEP_COLD_OCEAN.id) {
            return FloorType.COLD;
        } else if (biomeId == EnumBiome.FROZEN_OCEAN.id || biomeId == EnumBiome.DEEP_FROZEN_OCEAN.id) {
            return FloorType.FROZEN;
        }
        return FloorType.NORMAL;
    }

    private void replaceFloor(FullChunk chunk, int x, int floorY, int z, FloorType type, NukkitRandom random) {
        // Top layer of floor
        int topBlock;
        int r = random.nextBoundedInt(100);

        switch (type) {
            case WARM:
                topBlock = (r < 80) ? BlockID.SAND : BlockID.GRAVEL;
                break;
            case LUKEWARM:
                topBlock = (r < 60) ? BlockID.SAND : (r < 85) ? BlockID.GRAVEL : BlockID.CLAY_BLOCK;
                break;
            case COLD:
                topBlock = (r < 70) ? BlockID.GRAVEL : BlockID.SAND;
                break;
            case FROZEN:
                topBlock = (r < 50) ? BlockID.GRAVEL : (r < 80) ? BlockID.SAND : BlockID.CLAY_BLOCK;
                break;
            default:
                topBlock = (r < 50) ? BlockID.SAND : BlockID.GRAVEL;
        }

        chunk.setBlockId(x, floorY, z, topBlock);

        // Layers below floor
        for (int y = floorY - 1; y > floorY - 4 && y > 0; y--) {
            int belowId = chunk.getBlockId(x, y, z);
            if (belowId == BlockID.STONE || belowId == BlockID.DIRT) {
                int layerBlock;
                switch (type) {
                    case WARM:
                        layerBlock = BlockID.SAND;
                        break;
                    case COLD:
                    case FROZEN:
                        layerBlock = (random.nextBoundedInt(100) < 40) ? BlockID.GRAVEL : BlockID.STONE;
                        break;
                    default:
                        layerBlock = (random.nextBoundedInt(100) < 60) ? BlockID.SAND : BlockID.GRAVEL;
                }
                chunk.setBlockId(x, y, z, layerBlock);
            }
        }
    }

    private void addDepthVariation(FullChunk chunk, int x, int floorY, int z, FloorType type, NukkitRandom random) {
        // Small underwater hills (1-3 blocks high)
        if (random.nextBoundedInt(100) < 8) {
            int hillHeight = 1 + random.nextBoundedInt(3);
            int hillBlock = (type == FloorType.WARM) ? BlockID.SAND : BlockID.GRAVEL;

            for (int h = 1; h <= hillHeight; h++) {
                int hy = floorY + h;
                if (hy < Normal.seaHeight - 2) {
                    if (chunk.getBlockId(x, hy, z) == BlockID.STILL_WATER || chunk.getBlockId(x, hy, z) == BlockID.WATER) {
                        chunk.setBlockId(x, hy, z, hillBlock);
                    }
                }
            }
        }

        // Small pits (1-2 blocks deep)
        if (random.nextBoundedInt(100) < 5) {
            int pitDepth = 1 + random.nextBoundedInt(2);
            for (int d = 1; d <= pitDepth; d++) {
                int py = floorY - d;
                if (py > 5) {
                    chunk.setBlockId(x, py, z, BlockID.STILL_WATER);
                }
            }
        }
    }

    private void addIce(FullChunk chunk, int x, int surfaceY, int z, NukkitRandom random) {
        // Ice on water surface in frozen oceans
        if (surfaceY == Normal.seaHeight && random.nextBoundedInt(100) < 70) {
            chunk.setBlockId(x, surfaceY, z, BlockID.ICE);
        }

        // Packed ice below occasionally
        if (random.nextBoundedInt(100) < 10) {
            int iceY = surfaceY - random.nextBoundedInt(5) - 1;
            if (iceY > 0) {
                chunk.setBlockId(x, iceY, z, BlockID.PACKED_ICE);
            }
        }
    }

    private enum FloorType {
        WARM, LUKEWARM, COLD, FROZEN, NORMAL
    }
}
