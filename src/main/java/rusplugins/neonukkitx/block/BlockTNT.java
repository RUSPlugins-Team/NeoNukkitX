package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityPrimedTNT;
import rusplugins.neonukkitx.entity.projectile.EntityArrow;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.level.Sound;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.DoubleTag;
import rusplugins.neonukkitx.nbt.tag.FloatTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;
import rusplugins.neonukkitx.utils.BlockColor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created on 2015/12/8 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockTNT extends BlockSolid {

    @Override
    public String getName() {
        return "TNT";
    }

    @Override
    public int getId() {
        return TNT;
    }

    @Override
    public double getHardness() {
        return 0;
    }

    @Override
    public double getResistance() {
        return 0;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public int getBurnChance() {
        return 15;
    }

    @Override
    public int getBurnAbility() {
        return 100;
    }

    public void prime() {
        this.prime(80);
    }

    public void prime(int fuse) {
        prime(fuse, null);
    }

    public void prime(int fuse, Entity source) {
        this.getLevel().setBlock(this, Block.get(BlockID.AIR), true);
        double mot = (ThreadLocalRandom.current().nextFloat() * 2 - 1) * 6.283185307179586;
        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", this.x + 0.5))
                        .add(new DoubleTag("", this.y))
                        .add(new DoubleTag("", this.z + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", -Math.sin(mot) * 0.02))
                        .add(new DoubleTag("", 0.2))
                        .add(new DoubleTag("", -Math.cos(mot) * 0.02)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", 0))
                        .add(new FloatTag("", 0)))
                .putByte("Fuse", fuse);

        Entity.createEntity(EntityPrimedTNT.NETWORK_ID,
                this.getLevel().getChunk(this.getChunkX(), this.getChunkZ()), nbt, source).spawnToAll();
    }

    @Override
    public int onUpdate(int type) {
        if ((type == Level.BLOCK_UPDATE_NORMAL || type == Level.BLOCK_UPDATE_REDSTONE) && this.level.isBlockPowered(this)) {
            this.prime();
        }

        return 0;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (item.getId() == Item.FLINT_STEEL) {
            item.useOn(this);
            this.prime(80, player);
            return true;
        } else if (item.getId() == Item.FIRE_CHARGE) {
            if (!player.isCreative()) item.count--;
            this.level.addSound(this, Sound.MOB_GHAST_FIREBALL);
            this.prime(80, player);
            return true;
        } else if (item.hasEnchantment(Enchantment.ID_FIRE_ASPECT)) {
            item.useOn(this);
            this.prime(80, player);
            return true;
        }

        return false;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.TNT_BLOCK_COLOR;
    }

    @Override
    public boolean hasEntityCollision() {
        return true;
    }

    @Override
    public void onEntityCollide(Entity entity) {
        if (entity instanceof EntityArrow && entity.isOnFire()) {
            entity.close();
            this.prime();
        }
    }
}
