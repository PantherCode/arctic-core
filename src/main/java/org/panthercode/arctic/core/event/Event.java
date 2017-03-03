package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by architect on 28.02.17.
 */
public class Event<T extends EventArgs> {

    private final Set<EventHandler<T>> handlers;

    private final EventBus eventBus;

    private Event(EventBus bus) {
        this.eventBus = bus;

        this.handlers = new HashSet<>();
    }

    public static <T extends EventArgs> Event<T> register(EventBus bus) {
        ArgumentUtils.checkNotNull(bus, "event bus");

        return new Event<>(bus);
    }

    public synchronized boolean registerHandler(EventHandler<T> handler) {
        return handler != null && this.handlers.add(handler);
    }

    public synchronized boolean unregisterHandler(EventHandler<T> handler) {
        return this.containsHandler(handler) &&
                this.handlers.remove(handler);
    }

    public boolean containsHandler(EventHandler<T> handler) {
        return handler != null && this.handlers.contains(handler);
    }

    public int size() {
        return this.handlers.size();
    }

    public Set<EventHandler<T>> handlers() {
        return new HashSet<>(this.handlers);
    }

    public void raise(Object source, T e) {
        ArgumentUtils.checkNotNull(e, "event args");

        for (EventHandler<T> handler : this.handlers) {
            this.eventBus.process(handler, source, e);
        }
    }
}
