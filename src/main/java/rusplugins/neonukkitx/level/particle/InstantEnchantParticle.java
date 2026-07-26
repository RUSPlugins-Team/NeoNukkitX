package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.math.Vector3;

/**
 * Created on 2015/11/21 by xtypr.
 * Package rusplugins.neonukkitx.level.particle in project Nukkit .
 */
public class InstantEnchantParticle extends GenericParticle {

    public InstantEnchantParticle(Vector3 pos) {
        super(pos, Particle.TYPE_MOB_SPELL_INSTANTANEOUS);
    }
}
