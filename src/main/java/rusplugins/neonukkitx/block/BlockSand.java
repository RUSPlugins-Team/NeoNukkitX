package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemDye;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.level.generator.object.ObjectTallGrass;
import rusplugins.neonukkitx.level.particle.BoneMealParticle;
import rusplugins.neonukkitx.utils.BlockColor;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class BlockSand extends BlockFallableMeta {

    public static final int DEFAULT = 0;
    public static final int RED = 1;

    public BlockSand() {
        this(0);
    }

    public BlockSand(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return SAND;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SHOVEL;
    }

    @Override
    public String getName() {
        if (this.getDamage() == 0x01) {
            return "Red Sand";
        }

        return "Sand";
    }

    @Override
    public BlockColor getColor() {
        if (this.getDamage() == 0x01) {
            return BlockColor.ORANGE_BLOCK_COLOR;
        }

        return BlockColor.SAND_BLOCK_COLOR;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (player != null && item.getId() == Item.DYE && item.getDamage() == ItemDye.BONE_MEAL) {
            Block up = this.up();
            if (up instanceof BlockWater) {
                if (!player.isCreative()) {
                    item.count--;
                }
                this.level.addParticle(new BoneMealParticle(this));
                if (up.getDamage() == 0 && up.up() instanceof BlockWater) {
                    ObjectTallGrass.growSeagrass(this.getLevel(), this);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }
}
