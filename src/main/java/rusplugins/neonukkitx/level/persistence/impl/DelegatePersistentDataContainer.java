package rusplugins.neonukkitx.level.persistence.impl;

import rusplugins.neonukkitx.level.persistence.ImmutableCompoundTag;
import rusplugins.neonukkitx.level.persistence.PersistentDataContainer;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class DelegatePersistentDataContainer implements PersistentDataContainer {

    private PersistentDataContainer delegate;

    protected abstract PersistentDataContainer createDelegate();

    protected final PersistentDataContainer getDelegate() {
        if (this.delegate == null) {
            this.delegate = this.createDelegate();
        }
        return this.delegate;
    }

    @Override
    public CompoundTag getStorage() {
        return this.getDelegate().getStorage();
    }

    @Override
    public void setStorage(CompoundTag storage) {
        this.getDelegate().setStorage(storage);
    }

    @Override
    public CompoundTag getReadStorage() {
        return this.delegate == null ? ImmutableCompoundTag.EMPTY : this.getStorage();
    }
}
