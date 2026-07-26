package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.network.protocol.DataPacket;
import rusplugins.neonukkitx.network.protocol.LevelEventPacket;

/**
 * Created on 2015/11/21 by xtypr.
 * Package rusplugins.neonukkitx.level.particle in project Nukkit .
 */
public class ItemBreakParticle extends Particle {

    private final int data;

    public ItemBreakParticle(Vector3 pos, Item item) {
        super(pos.x, pos.y, pos.z);
        this.data = (item.getNetworkId() << 16 | item.getDamage());
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket packet = new LevelEventPacket();
        packet.evid = (short) (LevelEventPacket.EVENT_ADD_PARTICLE_MASK | Particle.TYPE_ITEM_BREAK);
        packet.x = (float) this.x;
        packet.y = (float) this.y;
        packet.z = (float) this.z;
        packet.data = this.data;
        packet.tryEncode();
        return new DataPacket[]{packet};
    }
}
