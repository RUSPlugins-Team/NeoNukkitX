package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;
import rusplugins.neonukkitx.nbt.tag.StringTag;
import rusplugins.neonukkitx.utils.TextFormat;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private final Item item;
    private final List<String> lore = new ArrayList<>();

    public ItemBuilder(int id) {
        this.item = Item.get(id);
    }

    public ItemBuilder(int id, int meta) {
        this.item = Item.get(id, meta);
    }

    public ItemBuilder amount(int amount) {
        this.item.setCount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        this.item.setCustomName(TextFormat.colorize(name));
        return this;
    }

    public ItemBuilder lore(String... lines) {
        for (String line : lines) {
            lore.add(TextFormat.colorize(line));
        }
        return this;
    }

    public ItemBuilder lore(List<String> lines) {
        for (String line : lines) {
            lore.add(TextFormat.colorize(line));
        }
        return this;
    }

    public ItemBuilder enchant(int id, int level) {
        Enchantment enchantment = Enchantment.getEnchantment(id);
        if (enchantment != null) {
            enchantment.setLevel(level);
            this.item.addEnchantment(enchantment);
        }
        return this;
    }

    public ItemBuilder nbt(String key, String value) {
        if (!this.item.hasCompoundTag()) {
            this.item.setCompoundTag(new CompoundTag());
        }
        this.item.getNamedTag().putString(key, value);
        return this;
    }

    public ItemBuilder nbt(String key, int value) {
        if (!this.item.hasCompoundTag()) {
            this.item.setCompoundTag(new CompoundTag());
        }
        this.item.getNamedTag().putInt(key, value);
        return this;
    }

    public ItemBuilder unbreakable(boolean value) {
        if (!this.item.hasCompoundTag()) {
            this.item.setCompoundTag(new CompoundTag());
        }
        this.item.getNamedTag().putByte("Unbreakable", value ? (byte) 1 : (byte) 0);
        return this;
    }

    public Item build() {
        if (!lore.isEmpty()) {
            if (!this.item.hasCompoundTag()) {
                this.item.setCompoundTag(new CompoundTag());
            }
            ListTag<StringTag> loreTag = new ListTag<>("Lore");
            for (String line : lore) {
                loreTag.add(new StringTag("", line));
            }
            this.item.getNamedTag().putList(loreTag);
        }
        return this.item;
    }
}
