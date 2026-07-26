package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityArmorStand;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.DoubleTag;
import rusplugins.neonukkitx.nbt.tag.FloatTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;
import rusplugins.neonukkitx.network.protocol.LevelEventPacket;

public class ItemArmorStand extends Item {

    public ItemArmorStand() {
        this(0);
    }

    public ItemArmorStand(Integer meta) {
        this(meta, 1);
    }

    public ItemArmorStand(Integer meta, int count) {
        super(ARMOR_STAND, meta, count, "Armor Stand");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        FullChunk chunk = level.getChunk((int) block.getX() >> 4, (int) block.getZ() >> 4);

        if (chunk == null) {
            return false;
        }

        for (Entity e : chunk.getEntities().values()) {
            if (e instanceof EntityArmorStand) {
                if (e.getY() == block.getY() && e.getX() == (block.getX() + 0.5) && e.getZ() == (block.getZ() + 0.5)) {
                    return false;
                }
            }
        }

        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", block.getX() + 0.5))
                        .add(new DoubleTag("", block.getY()))
                        .add(new DoubleTag("", block.getZ() + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", getDirection((float) player.getYaw())))
                        .add(new FloatTag("", 0)));

        if (this.hasCustomName()) {
            nbt.putString("CustomName", this.getCustomName());
        }

        Entity entity = Entity.createEntity("ArmorStand", chunk, nbt);

        if (!player.isCreative()) {
            this.count--;
        }

        if (entity != null) {
            entity.spawnToAll();
            player.getLevel().addLevelEvent(entity, LevelEventPacket.EVENT_SOUND_ARMOR_STAND_PLACE);
        }
        return true;
    }

    private static float getDirection(float yaw) {
        float rot = (Math.round(yaw / 22.5f / 2f) * 45f) - 180f;
        if (rot < 0) {
            rot += 360f;
        }
        return rot;
    }
}
