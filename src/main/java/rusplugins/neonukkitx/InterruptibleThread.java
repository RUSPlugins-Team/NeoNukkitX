package rusplugins.neonukkitx;

/**
 * An interface to describe a thread that can be interrupted.
 * <p>
 * When a Nukkit server is stopping, Nukkit finds all threads implements {@code InterruptibleThread},
 * and interrupt them one by one.
 *
 * @author MagicDroidX(code) @ Nukkit Project
 * @author 粉鞋大妈(javadoc) @ Nukkit Project
 * @see rusplugins.neonukkitx.scheduler.AsyncWorker
 */
public interface InterruptibleThread {
}
