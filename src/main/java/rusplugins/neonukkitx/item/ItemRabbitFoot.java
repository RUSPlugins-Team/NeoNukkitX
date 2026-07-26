package rusplugins.neonukkitx.item;

/**
 * Created by Snake1999 on 2016/1/14.
 * Package rusplugins.neonukkitx.item in project nukkit.
 */
public class ItemRabbitFoot extends Item {

    public ItemRabbitFoot() {
        this(0, 1);
    }

    public ItemRabbitFoot(Integer meta) {
        this(meta, 1);
    }

    public ItemRabbitFoot(Integer meta, int count) {
        super(RABBIT_FOOT, meta, count, "Rabbit Foot");
    }
}
