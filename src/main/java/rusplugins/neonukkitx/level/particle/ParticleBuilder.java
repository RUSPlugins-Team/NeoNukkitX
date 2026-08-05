package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.Vector3;

/**
 * Fluent API for spawning particles.
 */
public class ParticleBuilder {
    private final Level level;
    private final int particleId;
    private Vector3 position;
    private int data = 0;

    public ParticleBuilder(Level level, int particleId) {
        this.level = level;
        this.particleId = particleId;
    }

    public ParticleBuilder position(double x, double y, double z) {
        this.position = new Vector3(x, y, z);
        return this;
    }

    public ParticleBuilder position(Vector3 pos) {
        this.position = pos;
        return this;
    }

    public ParticleBuilder data(int data) {
        this.data = data;
        return this;
    }

    public void spawn() {
        if (position == null) throw new IllegalStateException("Position not set");
        level.addParticle(new GenericParticle(position, particleId, data));
    }
}
