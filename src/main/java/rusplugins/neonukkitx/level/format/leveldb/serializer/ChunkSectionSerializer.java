package rusplugins.neonukkitx.level.format.leveldb.serializer;

import rusplugins.neonukkitx.level.format.leveldb.structure.ChunkBuilder;
import rusplugins.neonukkitx.level.format.leveldb.structure.StateBlockStorage;
import io.netty.buffer.ByteBuf;

interface ChunkSectionSerializer {

    void serialize(ByteBuf buf, StateBlockStorage[] storage, int ySection);

    StateBlockStorage[] deserialize(ByteBuf buf, ChunkBuilder builder);
}
