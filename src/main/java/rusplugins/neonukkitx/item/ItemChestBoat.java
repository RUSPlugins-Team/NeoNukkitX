package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockWater;
import rusplugins.neonukkitx.entity.item.EntityChestBoat;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.DoubleTag;
import rusplugins.neonukkitx.nbt.tag.FloatTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;

public abstract class ItemChestBoat extends Item {

    protected ItemChestBoat(int id, int meta, int count, String name) {
        super(id, meta, count, name);
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        if (face != BlockFace.UP) return false;
        EntityChestBoat boat = new EntityChestBoat(
                level.getChunk(block.getChunkX(), block.getChunkZ()), new CompoundTag("")
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", block.getX() + 0.5))
                        .add(new DoubleTag("", block.getY() - (target instanceof BlockWater ? 0.1 : 0)))
                        .add(new DoubleTag("", block.getZ() + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", (float) ((player.yaw + 90f) % 360)))
                        .add(new FloatTag("", 0)))
                .putInt("Variant", this.getVariant())
        );

        if (!player.isCreative()) {
            this.count--;
        }

        boat.spawnToAll();
        return true;
    }

    protected abstract int getVariant();

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
