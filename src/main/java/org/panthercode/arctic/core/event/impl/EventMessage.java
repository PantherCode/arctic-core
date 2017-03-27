package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventHandler;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.Message;
import org.panthercode.arctic.core.runtime.MessageConsumeFailure;

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
     *
     */
    private final Handler<Message<T>> messageHandler;

    /**
     *
     */
    private Handler<MessageConsumeFailure<T>> exceptionHandler;

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
     * @param content
     * @param eventHandler
     */
    public EventMessage(Object source,
                        T content,
                        EventHandler<T> eventHandler) {
        this(source, content, eventHandler, null, null);
    }

    /**
     * @param source
     * @param content
     * @param eventHandler
     * @param messageHandler
     */
    public EventMessage(Object source,
                        T content,
                        EventHandler<T> eventHandler,
                        Handler<Message<T>> messageHandler,
                        Handler<MessageConsumeFailure<T>> exceptionHandler) {
        this.content = content;
        this.source = source;
        this.eventHandler = eventHandler;
        this.messageHandler = messageHandler;
        this.exceptionHandler = exceptionHandler;

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
                this.eventHandler.handle(this.source, this.content);
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
    public T content() {
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

    /**
     * @return
     */
    @Override
    public Handler<Message<T>> consumeHandler() {
        return this.messageHandler;
    }

    /**
     * @param exceptionHandler
     */
    @Override
    public void setExceptionHandler(Handler<MessageConsumeFailure<T>> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * @return
     */
    @Override
    public Handler<MessageConsumeFailure<T>> getExceptionHandler() {
        return this.exceptionHandler;
    }
}