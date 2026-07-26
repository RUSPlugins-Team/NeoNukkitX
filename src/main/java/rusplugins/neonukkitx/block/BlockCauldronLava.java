package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.blockentity.BlockEntity;
import rusplugins.neonukkitx.blockentity.BlockEntityCauldron;
import rusplugins.neonukkitx.entity.BaseEntity;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.event.entity.EntityCombustByBlockEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageByBlockEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageEvent;
import rusplugins.neonukkitx.event.player.PlayerBucketFillEvent;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.level.Sound;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.potion.Effect;

public class BlockCauldronLava extends BlockCauldron {

    public BlockCauldronLava() {
        this(0x8);
    }

    public BlockCauldronLava(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Lava Cauldron";
    }

    @Override
    public int getId() {
        return LAVA_CAULDRON;
    }

    @Override
    public int getLightLevel() {
        return 15;
    }

    @Override
    public boolean hasEntityCollision() {
        return true;
    }

    @Override
    public void setFillLevel(int fillLevel) {
        super.setFillLevel(fillLevel);
        setDamage(getDamage() | 0x8);
    }

    @Override
    public void onEntityCollide(Entity entity) {
        if (!entity.fireProof || !entity.isOnFire() || !(entity instanceof BaseEntity)) { // Improve performance
            if (!entity.fireProof || !entity.isOnFire()) {

                EntityCombustByBlockEvent ev = new EntityCombustByBlockEvent(this, entity, 8);
                Server.getInstance().getPluginManager().callEvent(ev);
                if (!ev.isCancelled() && entity.isAlive() && entity.noDamageTicks == 0) {
                    entity.setOnFire(ev.getDuration());
                }
            }

            if (!entity.hasEffect(Effect.FIRE_RESISTANCE)) {
                entity.attack(new EntityDamageByBlockEvent(this, entity, EntityDamageEvent.DamageCause.LAVA, 4));
            }
        }
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (item.getId() == ItemID.BUCKET) {
            if (item.getDamage() == 0) {
                if (!isFull()) {
                    return false;
                }

                PlayerBucketFillEvent ev = new PlayerBucketFillEvent(player, this, null, item, Item.get(ItemID.BUCKET, 10, 1));
                this.level.getServer().getPluginManager().callEvent(ev);
                if (!ev.isCancelled()) {
                    replaceBucket(item, player, ev.getItem());
                    if (!(this.level.getBlockEntity(this) instanceof BlockEntityCauldron)) {
                        BlockEntity.createBlockEntity(BlockEntity.CAULDRON, this.getChunk(), new CompoundTag("")
                                .putString("id", BlockEntity.CAULDRON)
                                .putInt("x", (int) this.x)
                                .putInt("y", (int) this.y)
                                .putInt("z", (int) this.z)
                                .putShort("PotionId", 0xffff)
                                .putByte("SplashPotion", 0));
                    }
                    this.level.setBlock(this, Block.get(CAULDRON_BLOCK), true);
                    this.getLevel().addSound(this.add(0.5, 0.5, 0.5), Sound.BUCKET_FILL_LAVA);
                }
            }
        }

        this.level.updateComparatorOutputLevel(this);
        return true;
    }

    @Override
    public boolean isFull() {
        return this.getDamage() == 14;
    }

    @Override
    public int onUpdate(int type) {
        return 0;
    }
}
