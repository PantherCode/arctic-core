package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventHandler;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.message.Message;
import org.panthercode.arctic.core.runtime.message.MessageResponse;

/**
 * Created by architect on 26.03.17.
 */
public class EventMessage<T extends EventArgs> implements Message<T> {
    /**
     *
     */
    private final T content;

    /**
     *
     */
    private final Object source;

    /**
     *
     */
    private final EventHandler<T> eventHandler;

    /**
     * @param source
     * @param content
     * @param eventHandler
     */
    public EventMessage(Object source,
                        T content,
                        EventHandler<T> eventHandler) {
        this.content = content;
        this.source = source;
        this.eventHandler = eventHandler;
    }

    /**
     *
     */
    @Override
    public MessageResponse<T> consume() {
        if (this.eventHandler != null) {
            try {
                this.eventHandler.handle(this.source, this.content);
            } catch (Exception e) {
                return this.fail(500, "An internal error is occurred.");
            }
        }

        return MessageResponse.ok(this.body());
    }

    @Override
    public MessageResponse<T> fail(int code, String message) {
        return MessageResponse.fail(this.body(), code, message);
    }

    /**
     * @return
     */
    @Override
    public T body() {
        return this.content;
    }

    /**
     * @return
     */
    public EventHandler<T> eventHandler() {
        return this.eventHandler;
    }

    /**
     * @return
     */
    public Object source() {
        return this.source;
    }
}