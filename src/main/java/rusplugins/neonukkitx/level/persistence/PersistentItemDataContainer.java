package rusplugins.neonukkitx.level.persistence;

public interface PersistentItemDataContainer extends PersistentDataContainer {

    void setConvertsToBlock(boolean value);

    boolean convertsToBlock();
}
