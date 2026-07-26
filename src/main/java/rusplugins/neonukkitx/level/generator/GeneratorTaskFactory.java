package rusplugins.neonukkitx.level.generator;

import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.level.format.generic.BaseFullChunk;
import rusplugins.neonukkitx.scheduler.AsyncTask;

public interface GeneratorTaskFactory {

    AsyncTask populateChunkTask(BaseFullChunk chunk, Level level);
    AsyncTask generateChunkTask(BaseFullChunk chunk, Level level);
}
