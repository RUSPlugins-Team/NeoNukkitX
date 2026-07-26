package rusplugins.neonukkitx.item.custom;

public interface CustomItem {

    ItemDefinition getItemDefinition();

    default String getIdentifier() {
        return this.getItemDefinition().getIdentifier();
    }
}
