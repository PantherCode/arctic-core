package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventHandler;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.Message;
import org.panthercode.arctic.core.runtime.MessageConsumeFailure;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by architect on 28.02.17.
 */
public final class DefaultEvent<T extends EventArgs> implements Event<T> {

    /**
     *
     */
    private final Set<EventHandler<T>> eventHandlers;

    /**
     *
     */
    private final EventBus eventBus;

    /**
     *
     */
    private final Handler<Message<T>> consumeHandler;

    /**
     *
     */
    private Handler<MessageConsumeFailure<T>> exceptionHandler;

    /**
     * @param factory
     */
    public DefaultEvent(DefaultEventFactory<T> factory) {
        this.eventBus = factory.getEventBus();

        this.consumeHandler = factory.getConsumeHandler();

        this.exceptionHandler = factory.getExceptionHandler();

        this.eventHandlers = new HashSet<>();
    }

    /**
     * @param handler
     * @return
     */
    @Override
    public boolean addHandler(EventHandler<T> handler) {
        return handler != null && this.eventHandlers.add(handler);
    }

    /**
     * @param handler
     * @return
     */
    @Override
    public boolean removeHandler(EventHandler<T> handler) {
        return handler != null && this.eventHandlers.remove(handler);
    }

    /**
     * @param handler
     * @return
     */
    @Override
    public boolean hasHandler(EventHandler<T> handler) {
        return handler != null && this.eventHandlers.contains(handler);
    }

    @Override
    public Set<EventHandler<T>> handlers() {
        return this.eventHandlers;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return this.eventHandlers.size();
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return this.eventHandlers.isEmpty();
    }

    /**
     * @param source
     * @param eventArgs
     */
    @Override
    public void send(Object source, T eventArgs) {
        if (!this.isEmpty()) {
            for (EventHandler<T> handler : this.eventHandlers) {
                this.eventBus.process(new EventMessage<>(source, eventArgs, handler, this.consumeHandler, exceptionHandler));
            }
        }
    }
}
