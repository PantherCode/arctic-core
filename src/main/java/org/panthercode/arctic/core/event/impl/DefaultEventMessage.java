package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventHandler;
import org.panthercode.arctic.core.event.EventMessage;
import org.panthercode.arctic.core.event.Handler;

/**
 * Created by architect on 26.03.17.
 */
public class DefaultEventMessage<T extends EventArgs> implements EventMessage<T> {
    /**
     *
     */
    private final T data;

    /**
     *
     */
    private final Object source;

    /**
     *
     */
    private final EventHandler<T> eventHandler;

    /**
     *
     */
    private final Handler<EventMessage<T>> messageHandler;

    /**
     *
     */
    private boolean isConsumed;

    /**
     *
     */
    private boolean isFailed;

    /**
     * @param source
     * @param data
     * @param eventHandler
     */
    public DefaultEventMessage(Object source,
                               T data,
                               EventHandler<T> eventHandler) {
        this(source, data, eventHandler, null);
    }

    /**
     * @param source
     * @param data
     * @param eventHandler
     * @param messageHandler
     */
    public DefaultEventMessage(Object source,
                               T data,
                               EventHandler<T> eventHandler,
                               Handler<EventMessage<T>> messageHandler) {
        this.data = data;
        this.source = source;
        this.eventHandler = eventHandler;
        this.messageHandler = messageHandler;

        this.isConsumed = false;
        this.isFailed = false;
    }

    /**
     *
     */
    @Override
    public void consume() {
        if (this.eventHandler != null) {
            try {
                this.eventHandler.handle(this.source, this.data);
            } catch (Exception e) {
                this.isFailed = true;
            }
        }

        this.isConsumed = true;

        if (this.messageHandler != null) {
            this.messageHandler.handle(this);
        }
    }

    /**
     * @return
     */
    @Override
    public boolean isConsumed() {
        return this.isConsumed;
    }

    /**
     * @return
     */
    @Override
    public boolean isFailed() {
        return this.isFailed;
    }

    /**
     * @return
     */
    @Override
    public T data() {
        return this.data;
    }

    /**
     * @return
     */
    @Override
    public Object source() {
        return this.source;
    }

    /**
     * @return
     */
    @Override
    public EventHandler<T> eventHandler() {
        return this.eventHandler;
    }

    /**
     * @return
     */
    @Override
    public Handler<EventMessage<T>> messageHandler() {
        return this.messageHandler;
    }
}
