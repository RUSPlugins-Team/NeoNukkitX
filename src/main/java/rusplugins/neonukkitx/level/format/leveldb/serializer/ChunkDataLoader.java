package rusplugins.neonukkitx.level.format.leveldb.serializer;

import rusplugins.neonukkitx.level.format.leveldb.LevelDBProvider;
import rusplugins.neonukkitx.level.format.leveldb.structure.LevelDBChunk;

public interface ChunkDataLoader {

    void initChunk(LevelDBChunk chunk, LevelDBProvider provider);
}
