package rusplugins.neonukkitx.event;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Type-safe event bus with WeakReference support.
 * Prevents memory leaks from stale listeners.
 */
public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final Map<Class<? extends Event>, List<WeakListener<? extends Event>>> listeners = new ConcurrentHashMap<>();

    private EventBus() {}

    public <T extends Event> void subscribe(Class<T> eventClass, Consumer<T> handler) {
        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(new WeakListener<>(handler));
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void post(T event) {
        List<WeakListener<? extends Event>> handlers = listeners.get(event.getClass());
        if (handlers == null) return;

        handlers.removeIf(wl -> !wl.isAlive());
        for (WeakListener<? extends Event> wl : handlers) {
            ((WeakListener<T>) wl).accept(event);
        }
    }

    public <T extends Event> void unregisterAll(Class<T> eventClass) {
        listeners.remove(eventClass);
    }

    public void clear() {
        listeners.clear();
    }

    public static EventBus getInstance() {
        return INSTANCE;
    }

    private static class WeakListener<T extends Event> {
        private final WeakReference<Consumer<T>> ref;

        WeakListener(Consumer<T> handler) {
            this.ref = new WeakReference<>(handler);
        }

        boolean isAlive() {
            return ref.get() != null;
        }

        void accept(T event) {
            Consumer<T> handler = ref.get();
            if (handler != null) {
                handler.accept(event);
            }
        }
    }
}
