package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.GlobalBlockPalette;
import rusplugins.neonukkitx.math.Vector3;

/**
 * Created on 2015/11/21 by xtypr.
 * Package rusplugins.neonukkitx.level.particle in project Nukkit .
 */
public class TerrainParticle extends GenericParticle {

    public TerrainParticle(Vector3 pos, Block block) {
        super(pos, Particle.TYPE_TERRAIN, GlobalBlockPalette.getOrCreateRuntimeId(block.getId(), block.getDamage()));
    }
}
