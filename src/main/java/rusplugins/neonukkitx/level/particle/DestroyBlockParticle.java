package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.GlobalBlockPalette;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.network.protocol.DataPacket;
import rusplugins.neonukkitx.network.protocol.LevelEventPacket;

/**
 * Created on 2015/11/21 by xtypr.
 * Package rusplugins.neonukkitx.level.particle in project Nukkit .
 */
public class DestroyBlockParticle extends Particle {

    protected final int data;

    public DestroyBlockParticle(Vector3 pos, Block block) {
        super(pos.x, pos.y, pos.z);
        this.data = GlobalBlockPalette.getOrCreateRuntimeId(block.getId(), block.getDamage());
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket packet = new LevelEventPacket();
        packet.evid = LevelEventPacket.EVENT_PARTICLE_DESTROY;
        packet.x = (float) this.x;
        packet.y = (float) this.y;
        packet.z = (float) this.z;

        packet.data = this.data;
        packet.tryEncode();
        return new DataPacket[]{packet};
    }
}
