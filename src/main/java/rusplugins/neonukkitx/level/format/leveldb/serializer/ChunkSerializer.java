package rusplugins.neonukkitx.level.format.leveldb.serializer;

import rusplugins.neonukkitx.level.format.Chunk;
import rusplugins.neonukkitx.level.format.leveldb.structure.ChunkBuilder;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;

public interface ChunkSerializer {

    void serialize(WriteBatch db, Chunk chunk);

    void deserialize(DB db, ChunkBuilder chunkBuilder);
}
