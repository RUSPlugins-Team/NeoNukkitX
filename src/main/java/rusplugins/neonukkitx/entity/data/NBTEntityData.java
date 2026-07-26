package rusplugins.neonukkitx.entity.data;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class NBTEntityData extends EntityData<CompoundTag> {

    public CompoundTag tag;
    public Item item;

    public NBTEntityData(int id, CompoundTag tag) {
        super(id);
        this.tag = tag;
    }

    public NBTEntityData(int id, Item item) {
        super(id);
        this.item = item;
        this.tag = item.getNamedTag();
    }

    @Override
    public CompoundTag getData() {
        return this.tag;
    }

    @Override
    public void setData(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public int getType() {
        return Entity.DATA_TYPE_NBT;
    }

    @Override
    public String toString() {
        return tag.toString();
    }
}
