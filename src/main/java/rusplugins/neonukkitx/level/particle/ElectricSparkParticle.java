package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.network.protocol.DataPacket;
import rusplugins.neonukkitx.network.protocol.LevelEventPacket;

public class ElectricSparkParticle extends GenericParticle {

    public ElectricSparkParticle(Vector3 pos) {
        super(pos, Particle.TYPE_WAX);
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket packet = new LevelEventPacket();
        packet.evid = LevelEventPacket.EVENT_PARTICLE_ELECTRIC_SPARK;
        packet.x = (float) this.x;
        packet.y = (float) this.y;
        packet.z = (float) this.z;
        packet.data = this.data;
        packet.tryEncode();
        return new DataPacket[]{packet};
    }
}