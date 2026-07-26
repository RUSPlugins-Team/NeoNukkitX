package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.event.block.BlockFormEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageByBlockEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageEvent;
import rusplugins.neonukkitx.inventory.PlayerInventory;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.level.GameRule;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.potion.Effect;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockMagma extends BlockSolid {

    @Override
    public int getId() {
        return MAGMA;
    }

    @Override
    public String getName() {
        return "Magma Block";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public double getResistance() {
        return 0.5;
    }

    @Override
    public int getLightLevel() {
        return 3;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe()) {
            return new Item[]{
                    toItem()
            };
        } else {
            return new Item[0];
        }
    }

    @Override
    public void onEntityCollide(Entity entity) {
        if (entity.y >= this.y + 1 && !entity.hasEffect(Effect.FIRE_RESISTANCE)) {
            if (entity instanceof Player) {
                Player p = (Player) entity;
                PlayerInventory inv = p.getInventory();
                if (inv == null || inv.getBootsFast().hasEnchantment(Enchantment.ID_FROST_WALKER) || !entity.level.gameRules.getBoolean(GameRule.FIRE_DAMAGE)) {
                    return;
                }
                if (!p.isCreative() && !p.isSpectator() && !p.isSneaking()) {
                    entity.attack(new EntityDamageByBlockEvent(this, entity, EntityDamageEvent.DamageCause.MAGMA, 1));
                }
            } else {
                entity.attack(new EntityDamageByBlockEvent(this, entity, EntityDamageEvent.DamageCause.MAGMA, 1));
            }
        }
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.NETHERRACK_BLOCK_COLOR;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
    
    @Override
    public boolean hasEntityCollision() {
        return true;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            Block up = this.up();
            if (up instanceof BlockWater && (up.getDamage() == 0 || up.getDamage() == 8)) {
                BlockFormEvent event = new BlockFormEvent(up, Block.get(BUBBLE_COLUMN, BlockBubbleColumn.DIRECTION_DOWN));
                Server.getInstance().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    this.getLevel().setBlock(up, event.getNewState(), false, true);
                }
            }
        }

        return 0;
    }
}
