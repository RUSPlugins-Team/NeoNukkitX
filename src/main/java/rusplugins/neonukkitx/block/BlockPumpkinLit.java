package rusplugins.neonukkitx.block;

/**
 * Created on 2015/12/8 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockPumpkinLit extends BlockPumpkin {

    public BlockPumpkinLit() {
        this(0);
    }

    public BlockPumpkinLit(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Jack o'Lantern";
    }

    @Override
    public int getId() {
        return LIT_PUMPKIN;
    }

    @Override
    public int getLightLevel() {
        return 15;
    }

    @Override
    public boolean canBeActivated() {
        return false;
    }
}
