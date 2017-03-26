package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventHandler;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.Message;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by architect on 28.02.17.
 */
public final class DefaultEvent<T extends EventArgs> implements Event<T> {

    /**
     *
     */
    private final Set<EventHandler<T>> handlers;

    /**
     *
     */
    private final EventBus eventBus;

    /**
     * @param eventBus
     */
    public DefaultEvent(EventBus eventBus) {
        this.eventBus = ArgumentUtils.checkNotNull(eventBus, "event bus");

        this.handlers = new HashSet<>();
    }

    /**
     * @param handler
     * @return
     */
    @Override
    public boolean addHandler(EventHandler<T> handler) {
        return handler != null && this.handlers.add(handler);
    }

    /**
     * @param handler
     * @return
     */
    @Override
    public boolean removeHandler(EventHandler<T> handler) {
        return handler != null && this.handlers.remove(handler);
    }

    /**
     * @param handler
     * @return
     */
    @Override
    public boolean hasHandler(EventHandler<T> handler) {
        return handler != null && this.handlers.contains(handler);
    }

    @Override
    public Set<EventHandler<T>> handlers() {
        return this.handlers;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return this.handlers.size();
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return this.handlers.isEmpty();
    }

    /**
     * @param source
     * @param eventArgs
     */
    @Override
    public void send(Object source, T eventArgs) {
        this.send(source, eventArgs, null);
    }

    /**
     * @param source
     * @param data
     * @param messageHandler
     */
    @SuppressWarnings("unchecked")
    public void send(Object source, T data, Handler<Message<T>> messageHandler) {
        if (!this.isEmpty()) {
            for (EventHandler<T> eventHandler : this.handlers) {
                this.eventBus.process(new EventMessage(source, data, eventHandler, messageHandler));
            }
        }
    }
}
