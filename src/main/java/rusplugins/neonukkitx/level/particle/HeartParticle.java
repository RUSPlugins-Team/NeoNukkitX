package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.math.Vector3;

/**
 * Created on 2015/11/21 by xtypr.
 * Package rusplugins.neonukkitx.level.particle in project Nukkit .
 */
public class HeartParticle extends GenericParticle {

    public HeartParticle(Vector3 pos) {
        this(pos, 0);
    }

    public HeartParticle(Vector3 pos, int scale) {
        super(pos, Particle.TYPE_HEART, scale);
    }
}
