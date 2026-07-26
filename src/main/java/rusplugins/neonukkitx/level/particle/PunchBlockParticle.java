package rusplugins.neonukkitx.level.particle;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.GlobalBlockPalette;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.network.protocol.DataPacket;
import rusplugins.neonukkitx.network.protocol.LevelEventPacket;

public class PunchBlockParticle extends Particle {

    protected final int blockId;
    protected final int blockDamage;
    protected final int index;
    protected final int face;

    public PunchBlockParticle(Vector3 pos, Block block, BlockFace face) {
        this(pos, block.getId(), block.getDamage(), face);
    }

    public PunchBlockParticle(Vector3 pos, int blockId, int blockDamage, BlockFace face) {
        super(pos.x, pos.y, pos.z);
        this.blockId = blockId;
        this.blockDamage = blockDamage;
        this.face = face.getIndex();
        this.index = this.face << 24;
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket packet = new LevelEventPacket();
        packet.evid = LevelEventPacket.EVENT_PARTICLE_PUNCH_BLOCK;
        packet.x = (float) this.x;
        packet.y = (float) this.y;
        packet.z = (float) this.z;
        packet.data = GlobalBlockPalette.getOrCreateRuntimeId(blockId, blockDamage) | index;
        packet.tryEncode();
        return new DataPacket[]{packet};
    }
}
