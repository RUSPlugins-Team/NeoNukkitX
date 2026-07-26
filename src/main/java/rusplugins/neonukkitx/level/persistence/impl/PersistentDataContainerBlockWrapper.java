package rusplugins.neonukkitx.level.persistence.impl;

import rusplugins.neonukkitx.blockentity.BlockEntity;
import rusplugins.neonukkitx.level.persistence.ImmutableCompoundTag;
import rusplugins.neonukkitx.level.persistence.PersistentDataContainer;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class PersistentDataContainerBlockWrapper implements PersistentDataContainer {

    private final BlockEntity blockEntity;
    private CompoundTag storage;

    public PersistentDataContainerBlockWrapper(BlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public CompoundTag getReadStorage() {
        CompoundTag storage = this.getInternalStorage();
        if (storage == null) {
            return ImmutableCompoundTag.EMPTY;
        }
        return storage;
    }

    @Override
    public CompoundTag getStorage() {
        CompoundTag storage = this.getInternalStorage();
        if (storage == null) {
            storage = new CompoundTag();
            this.setStorage(storage);
        }
        return storage;
    }

    private CompoundTag getInternalStorage() {
        if (this.storage != null) {
            return this.storage;
        }

        if (this.blockEntity.namedTag.contains(STORAGE_TAG)) {
            return this.storage = this.blockEntity.namedTag.getCompound(STORAGE_TAG);
        }
        return null;
    }

    @Override
    public void setStorage(CompoundTag storage) {
        this.blockEntity.namedTag.putCompound(STORAGE_TAG, storage);
        this.storage = storage;
    }

    @Override
    public void clearStorage() {
        this.blockEntity.namedTag.remove(STORAGE_TAG);
        this.storage = null;
    }
}
