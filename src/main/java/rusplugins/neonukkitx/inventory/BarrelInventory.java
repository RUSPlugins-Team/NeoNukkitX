package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockBarrel;
import rusplugins.neonukkitx.blockentity.BlockEntityBarrel;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.network.protocol.LevelSoundEventPacket;

public class BarrelInventory extends ContainerInventory {

    public BarrelInventory(BlockEntityBarrel barrel) {
        super(barrel, InventoryType.BARREL);
    }

    @Override
    public BlockEntityBarrel getHolder() {
        return (BlockEntityBarrel) this.holder;
    }

    @Override
    public void onOpen(Player who) {
        super.onOpen(who);
        if (this.getViewers().size() != 1) {
            return;
        }

        BlockEntityBarrel barrel = this.getHolder();
        Level level = barrel.getLevel();
        if (level == null) {
            return;
        }

        Block block = barrel.getBlock();
        if (block instanceof BlockBarrel) {
            BlockBarrel blockBarrel = (BlockBarrel) block;
            if (!blockBarrel.isOpen()) {
                blockBarrel.setOpen(true);
                level.setBlock(blockBarrel, blockBarrel, true, true);
                level.addLevelSoundEvent(blockBarrel, LevelSoundEventPacket.SOUND_BLOCK_BARREL_OPEN);
            }
        }
    }

    @Override
    public void onClose(Player who) {
        super.onClose(who);
        if (!this.getViewers().isEmpty()) {
            return;
        }

        BlockEntityBarrel barrel = this.getHolder();
        Level level = barrel.getLevel();
        if (level == null) {
            return;
        }

        Block block = barrel.getBlock();
        if (block instanceof BlockBarrel) {
            BlockBarrel blockBarrel = (BlockBarrel) block;
            if (blockBarrel.isOpen()) {
                blockBarrel.setOpen(false);
                level.setBlock(blockBarrel, blockBarrel, true, true);
                level.addLevelSoundEvent(blockBarrel, LevelSoundEventPacket.SOUND_BLOCK_BARREL_CLOSE);
            }
        }
    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {
        super.onSlotChange(index, before, send);

        this.getHolder().chunk.setChanged();
    }
}
