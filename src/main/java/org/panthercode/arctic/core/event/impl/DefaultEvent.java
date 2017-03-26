package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by architect on 28.02.17.
 */
public final class DefaultEvent<T extends EventArgs> implements Event<T> {

    /**
     *
     */
    private final Set<EventHandler<T>> handlerSet;

    /**
     *
     */
    private final EventBus eventBus;

    /**
     *
     * @param eventBus
     */
    public DefaultEvent(EventBus eventBus) {
        this.eventBus = ArgumentUtils.checkNotNull(eventBus, "event bus");

        this.handlerSet = new HashSet<>();
    }

    /**
     *
     * @param handler
     * @return
     */
    @Override
    public boolean addHandler(EventHandler<T> handler) {
        return handler != null && this.handlerSet.add(handler);
    }

    /**
     *
     * @param handler
     * @return
     */
    @Override
    public boolean removeHandler(EventHandler<T> handler) {
        return handler != null && this.handlerSet.remove(handler);
    }

    /**
     *
     * @param handler
     * @return
     */
    @Override
    public boolean hasHandler(EventHandler<T> handler) {
        return handler != null && this.handlerSet.contains(handler);
    }

    /**
     *
     * @return
     */
    @Override
    public Set<EventHandler<T>> handlerSet() {
        return this.handlerSet;
    }

    /**
     *
     * @return
     */
    @Override
    public int size() {
        return this.handlerSet.size();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return this.handlerSet.isEmpty();
    }

    /**
     *
     * @param source
     * @param data
     */
    @Override
    public void raise(Object source, T data) {
        this.raise(source, data, null);
    }

    /**
     *
     * @param source
     * @param data
     * @param messageHandler
     */
    @Override
    @SuppressWarnings("unchecked")
    public void raise(Object source, T data, Handler<EventMessage<T>> messageHandler) {
        if (!this.isEmpty()) {
            for (EventHandler<T> eventHandler : this.handlerSet) {
                this.eventBus.process(new DefaultEventMessage(source, data, eventHandler, messageHandler));
            }
        }
    }
}
