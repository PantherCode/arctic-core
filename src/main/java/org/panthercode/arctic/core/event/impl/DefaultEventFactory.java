package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Event;
import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.EventFactory;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.runtime.Message;

/**
 * Created by architect on 26.03.17.
 */
public class DefaultEventFactory<T extends EventArgs> implements EventFactory<T> {

    private EventBus eventBus;

    private Handler<Message<T>> handler;

    public DefaultEventFactory(EventBus eventBus, Handler<Message<T>> handler) {
        this.eventBus = ArgumentUtils.checkNotNull(eventBus, "event bus");

        this.handler = handler;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = ArgumentUtils.checkNotNull(eventBus, "event bus");
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public void setHandler(Handler<Message<T>> handler) {
        this.handler = handler;
    }

    public Handler<Message<T>> getHandler() {
        return this.handler;
    }

    @Override
    public Event<T> register() {
        return new DefaultEvent<T>(this.eventBus, this.handler);
    }
}
