package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventFactory;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.Message;
import org.panthercode.arctic.core.runtime.MessageConsumeFailure;

/**
 * Created by architect on 26.03.17.
 */
public class DefaultEventFactory<T extends EventArgs> implements EventFactory<T> {

    /**
     *
     */
    private EventBus eventBus;

    /**
     *
     */
    private Handler<Message<T>> consumeHandler;

    /**
     *
     */
    private Handler<MessageConsumeFailure<T>> exceptionHandler;

    /**
     * @param eventBus
     */
    public DefaultEventFactory(EventBus eventBus) {
        this(eventBus, null);
    }

    /**
     * @param eventBus
     * @param consumeHandler
     */
    public DefaultEventFactory(EventBus eventBus,
                               Handler<Message<T>> consumeHandler) {
        this(eventBus, consumeHandler, null);
    }

    /**
     * @param eventBus
     * @param consumeHandler
     * @param exceptionHandler
     */
    public DefaultEventFactory(EventBus eventBus,
                               Handler<Message<T>> consumeHandler,
                               Handler<MessageConsumeFailure<T>> exceptionHandler) {
        this.eventBus = ArgumentUtils.checkNotNull(eventBus, "event bus");

        this.consumeHandler = consumeHandler;

        this.exceptionHandler = exceptionHandler;
    }

    /**
     * @param eventBus
     */
    public void setEventBus(EventBus eventBus) {
        this.eventBus = ArgumentUtils.checkNotNull(eventBus, "event bus");
    }

    /**
     * @return
     */
    public EventBus getEventBus() {
        return this.eventBus;
    }

    /**
     * @param consumeHandler
     */
    public void setConsumeHandler(Handler<Message<T>> consumeHandler) {
        this.consumeHandler = consumeHandler;
    }

    /**
     * @return
     */
    public Handler<Message<T>> getConsumeHandler() {
        return this.consumeHandler;
    }

    /**
     * @param exceptionHandler
     */
    public void setExceptionHandler(Handler<MessageConsumeFailure<T>> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * @return
     */
    public Handler<MessageConsumeFailure<T>> getExceptionHandler() {
        return this.exceptionHandler;
    }

    /**
     * @return
     */
    @Override
    public Event<T> register() {
        return new DefaultEvent<T>(this.eventBus, this.consumeHandler, this.exceptionHandler);
    }
}
