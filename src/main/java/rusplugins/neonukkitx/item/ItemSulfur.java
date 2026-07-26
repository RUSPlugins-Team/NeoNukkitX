package rusplugins.neonukkitx.item;

public class ItemSulfur extends Item {

    public ItemSulfur() {
        this(0, 1);
    }

    public ItemSulfur(Integer meta) {
        this(meta, 1);
    }

    public ItemSulfur(Integer meta, int count) {
        super(SULFUR, meta, count, "Sulfur");
    }
}
