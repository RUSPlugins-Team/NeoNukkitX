package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityFallingBlock;
import rusplugins.neonukkitx.event.block.BlockFallEvent;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.DoubleTag;
import rusplugins.neonukkitx.nbt.tag.FloatTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;

/**
 * @author rcsuperman
 * Nukkit Project
 */
public abstract class BlockFallableMeta extends BlockSolidMeta {

    protected BlockFallableMeta(int meta) {
        super(meta);
    }

    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            Block down = this.down();
            if (down.getId() == AIR || down instanceof BlockLiquid || down instanceof BlockFire) {
                BlockFallEvent event = new BlockFallEvent(this);
                this.level.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return type;
                }
                this.level.setBlock(this, Block.get(Block.AIR), true, true);
                CompoundTag nbt = new CompoundTag()
                        .putList(new ListTag<DoubleTag>("Pos")
                                .add(new DoubleTag("", this.x + 0.5))
                                .add(new DoubleTag("", this.y))
                                .add(new DoubleTag("", this.z + 0.5)))
                        .putList(new ListTag<DoubleTag>("Motion")
                                .add(new DoubleTag("", 0))
                                .add(new DoubleTag("", 0))
                                .add(new DoubleTag("", 0)))

                        .putList(new ListTag<FloatTag>("Rotation")
                                .add(new FloatTag("", 0))
                                .add(new FloatTag("", 0)))
                        .putInt("TileID", this.getId())
                        .putByte("Data", this.getDamage());

                Entity.createEntity(EntityFallingBlock.NETWORK_ID, this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt).spawnToAll();
            }
        }
        return type;
    }
}
