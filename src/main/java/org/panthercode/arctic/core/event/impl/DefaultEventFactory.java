package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventFactory;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.message.MessageResponse;

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
    private Handler<MessageResponse<T>> responseHandler;

    /**
     *
     */
    private Handler<Exception> exceptionHandler;

    /**
     * @param eventBus
     */
    public DefaultEventFactory(EventBus eventBus) {
        this(eventBus, null);
    }

    /**
     * @param eventBus
     * @param responseHandler
     */
    public DefaultEventFactory(EventBus eventBus,
                               Handler<MessageResponse<T>> responseHandler) {
        this(eventBus, responseHandler, null);
    }

    /**
     * @param eventBus
     * @param responseHandler
     * @param exceptionHandler
     */
    public DefaultEventFactory(EventBus eventBus,
                               Handler<MessageResponse<T>> responseHandler,
                               Handler<Exception> exceptionHandler) {
        this.eventBus = ArgumentUtils.checkNotNull(eventBus, "event bus");

        this.responseHandler = responseHandler;

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
     * @param responseHandler
     */
    public void setResponseHandler(Handler<MessageResponse<T>> responseHandler) {
        this.responseHandler = responseHandler;
    }

    /**
     * @return
     */
    public Handler<MessageResponse<T>> getResponseHandler() {
        return this.responseHandler;
    }

    /**
     * @param exceptionHandler
     */
    public void setExceptionHandler(Handler<Exception> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * @return
     */
    public Handler<Exception> getExceptionHandler() {
        return this.exceptionHandler;
    }

    /**
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Event<T> register() {
        return new DefaultEvent(this);
    }
}
