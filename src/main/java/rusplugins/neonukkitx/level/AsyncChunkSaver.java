package rusplugins.neonukkitx.level;

import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.format.generic.BaseLevelProvider;
import rusplugins.neonukkitx.scheduler.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Asynchronous chunk saver to prevent main thread blocking.
 * Chunks are queued and saved in background threads.
 */
public class AsyncChunkSaver {

    private final Server server;
    private final ConcurrentLinkedQueue<ChunkSaveEntry> saveQueue;
    private final int maxQueueSize;
    private boolean running;

    public AsyncChunkSaver(Server server) {
        this.server = server;
        this.saveQueue = new ConcurrentLinkedQueue<>();
        this.maxQueueSize = 500;
        this.running = true;
    }

    /**
     * Queues a chunk for async saving.
     */
    public void queueChunkSave(BaseLevelProvider provider, FullChunk chunk) {
        if (!running || saveQueue.size() >= maxQueueSize) {
            // Queue full — save synchronously to prevent memory leak
            provider.saveChunk(chunk.getX(), chunk.getZ(), chunk);
            return;
        }

        saveQueue.offer(new ChunkSaveEntry(provider, chunk));
    }

    /**
     * Processes the save queue. Should be called periodically.
     */
    public void processQueue() {
        int processed = 0;
        int maxPerTick = 10; // Save max 10 chunks per tick

        while (!saveQueue.isEmpty() && processed < maxPerTick) {
            ChunkSaveEntry entry = saveQueue.poll();
            if (entry == null) break;

            server.getScheduler().scheduleAsyncTask(new AsyncTask() {
                @Override
                public void onRun() {
                    entry.provider.saveChunk(entry.chunk.getX(), entry.chunk.getZ(), entry.chunk);
                }
            });

            processed++;
        }
    }

    /**
     * Saves all remaining chunks synchronously. Call on shutdown.
     */
    public void shutdown() {
        running = false;
        ChunkSaveEntry entry;
        while ((entry = saveQueue.poll()) != null) {
            entry.provider.saveChunk(entry.chunk.getX(), entry.chunk.getZ(), entry.chunk);
        }
    }

    public int getQueueSize() {
        return saveQueue.size();
    }

    private static class ChunkSaveEntry {
        final BaseLevelProvider provider;
        final FullChunk chunk;

        ChunkSaveEntry(BaseLevelProvider provider, FullChunk chunk) {
            this.provider = provider;
            this.chunk = chunk;
        }
    }
}
