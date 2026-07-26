package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.event.block.BlockRedstoneEvent;
import rusplugins.neonukkitx.event.entity.EntityInteractEvent;
import rusplugins.neonukkitx.event.player.PlayerInteractEvent;
import rusplugins.neonukkitx.event.player.PlayerInteractEvent.Action;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.AxisAlignedBB;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.SimpleAxisAlignedBB;
import rusplugins.neonukkitx.network.protocol.LevelSoundEventPacket;

/**
 * Created by Snake1999 on 2016/1/11.
 * Package rusplugins.neonukkitx.block in project nukkit
 */
public abstract class BlockPressurePlateBase extends BlockFlowable {

    protected float onPitch;
    protected float offPitch;

    protected BlockPressurePlateBase() {
        this(0);
    }

    protected BlockPressurePlateBase(int meta) {
        super(meta);
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public double getMinX() {
        return this.x + 0.625;
    }

    @Override
    public double getMinZ() {
        return this.z + 0.625;
    }

    @Override
    public double getMaxX() {
        return this.x + 0.9375;
    }

    @Override
    public double getMaxZ() {
        return this.z + 0.9375;
    }

    @Override
    public double getMaxY() {
        return this.isActivated() ? this.y + 0.03125 : this.y + 0.0625;
    }

    @Override
    public boolean isPowerSource() {
        return true;
    }

    public boolean isActivated() {
        return this.getDamage() == 0;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (!isSupportValid(this.down())) {
                this.level.useBreakOn(this, Item.get(Item.WOODEN_PICKAXE));
            }
        } else if (type == Level.BLOCK_UPDATE_SCHEDULED) {
            int power = this.getRedstonePower();

            if (power > 0) {
                this.updateState(power);
            }
        }

        return 0;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (!isSupportValid(this.down())) {
            return false;
        }

        this.getLevel().setBlock(this, this, true, true);
        return true;
    }

    private static boolean isSupportValid(Block block) {
        return !block.isTransparent() || block.isNarrowSurface() || Block.canStayOnFullSolid(block);
    }

    @Override
    protected AxisAlignedBB recalculateCollisionBoundingBox() {
        return new SimpleAxisAlignedBB(this.x + 0.125, this.y, this.z + 0.125, this.x + 0.875, this.y + 0.25, this.z + 0.875D);
    }

    @Override
    public void onEntityCollide(Entity entity) {
        int power = getRedstonePower();

        if (power == 0) {
            Event ev;

            if (entity instanceof Player) {
                ev = new PlayerInteractEvent((Player) entity, null, this, null, Action.PHYSICAL);
            } else {
                ev = new EntityInteractEvent(entity, this);
            }

            this.level.getServer().getPluginManager().callEvent(ev);

            if (!ev.isCancelled()) {
                updateState(power);
            }
        }
    }

    protected void updateState(int oldStrength) {
        int strength = this.computeRedstoneStrength();
        boolean wasPowered = oldStrength > 0;
        boolean isPowered = strength > 0;

        if (oldStrength != strength) {
            this.setRedstonePower(strength);
            this.level.setBlock(this, this, false, true);

            this.level.updateAroundRedstone(this, null);
            this.level.updateAroundRedstone(this.getSideVec(BlockFace.DOWN), null);

            if (!isPowered && wasPowered) {
                this.playOffSound();
                this.level.getServer().getPluginManager().callEvent(new BlockRedstoneEvent(this, 15, 0));
            } else if (isPowered && !wasPowered) {
                this.playOnSound();
                this.level.getServer().getPluginManager().callEvent(new BlockRedstoneEvent(this, 0, 15));
            }
        }

        if (isPowered) {
            this.level.scheduleUpdate(this, 20);
        }
    }

    @Override
    public boolean onBreak(Item item) {
        this.level.setBlock(this, Block.get(BlockID.AIR), true, true);

        if (this.getRedstonePower() > 0) {
            this.level.updateAroundRedstone(this, null);
            this.level.updateAroundRedstone(this.getSideVec(BlockFace.DOWN), null);
        }

        return true;
    }

    @Override
    public int getWeakPower(BlockFace side) {
        return getRedstonePower();
    }

    @Override
    public int getStrongPower(BlockFace side) {
        return side == BlockFace.UP ? this.getRedstonePower() : 0;
    }

    public int getRedstonePower() {
        return this.getDamage();
    }

    public void setRedstonePower(int power) {
        this.setDamage(power);
    }

    protected void playOnSound() {
        this.level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_POWER_ON);
    }

    protected void playOffSound() {
        this.level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_POWER_OFF);
    }

    protected abstract int computeRedstoneStrength();

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }

    @Override
    public boolean breakWhenPushed() {
        return true;
    }
}
