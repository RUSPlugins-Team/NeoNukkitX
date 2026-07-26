package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.blockentity.BlockEntity;
import rusplugins.neonukkitx.blockentity.BlockEntitySkull;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemSkull;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.math.AxisAlignedBB;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.SimpleAxisAlignedBB;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.Tag;
import rusplugins.neonukkitx.utils.BlockColor;
import rusplugins.neonukkitx.utils.Faceable;

/**
 * @author Justin
 */
public class BlockSkull extends BlockTransparentMeta implements Faceable {

    public BlockSkull() {
        this(0);
    }

    public BlockSkull(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return SKULL_BLOCK;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public double getResistance() {
        return 5;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public String getName() {
        int itemMeta = 0;

        if (this.level != null) {
            BlockEntity blockEntity = getLevel().getBlockEntity(this);
            if (blockEntity != null) itemMeta = blockEntity.namedTag.getByte("SkullType");
        }

        return ItemSkull.getItemSkullName(itemMeta);
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        switch (face) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
            case UP:
                this.setDamage(face.getIndex());
                break;
            case DOWN:
            default:
                return false;
        }
        this.getLevel().setBlock(this, this, true, true);

        CompoundTag nbt = new CompoundTag()
                .putString("id", BlockEntity.SKULL)
                .putByte("SkullType", item.getDamage())
                .putInt("x", (int) block.getX())
                .putInt("y", (int) block.getY())
                .putInt("z", (int) block.getZ())
                .putByte("Rot", (int) Math.floor((player.yaw * 16 / 360) + 0.5) & 0x0f);
        if (item.hasCustomBlockData()) {
            for (Tag aTag : item.getCustomBlockData().getAllTags()) {
                nbt.put(aTag.getName(), aTag);
            }
        }

        BlockEntitySkull blockEntity = (BlockEntitySkull) BlockEntity.createBlockEntity(BlockEntity.SKULL, this.getChunk(), nbt);
        blockEntity.spawnToAll();
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        BlockEntity blockEntity = getLevel().getBlockEntity(this);
        int dropMeta = 0;
        if (blockEntity != null) dropMeta = blockEntity.namedTag.getByte("SkullType");
        return new Item[]{
                Item.get(Item.SKULL, dropMeta)
        };
    }

    @Override
    public Item toItem() {
        BlockEntity blockEntity = getLevel().getBlockEntity(this);
        int itemMeta = 0;
        if (blockEntity != null) itemMeta = blockEntity.namedTag.getByte("SkullType");
        return Item.get(Item.SKULL, itemMeta);
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.AIR_BLOCK_COLOR;
    }
    @Override
    public BlockFace getBlockFace() {
        return BlockFace.fromIndex(this.getDamage() & 0x7);
    }

    @Override
    protected AxisAlignedBB recalculateBoundingBox() {
        AxisAlignedBB bb = new SimpleAxisAlignedBB(this.x + 0.25, this.y, this.z + 0.25, this.x + 1 - 0.25, this.y + 0.5, this.z + 1 - 0.25);
        switch (this.getBlockFace()) {
            case NORTH:
                return bb.offset(0, 0.25, 0.25);
            case SOUTH:
                return bb.offset(0, 0.25, -0.25);
            case WEST:
                return bb.offset(0.25, 0.25, 0);
            case EAST:
                return bb.offset(-0.25, 0.25, 0);
        }
        return bb;
    }

    @Override
    public WaterloggingType getWaterloggingType() {
        return WaterloggingType.WHEN_PLACED_IN_WATER;
    }

    @Override
    public boolean canBeFlowedInto() {
        return true;
    }

    @Override
    public boolean breakWhenPushed() {
        return true;
    }

    @Override
    public boolean alwaysDropsOnExplosion() {
        return true;
    }
}
