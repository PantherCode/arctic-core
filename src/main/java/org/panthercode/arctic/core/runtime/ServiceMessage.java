package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.event.Handler;

/**
 * Created by architect on 26.03.17.
 */
public interface ServiceMessage<T> {
    T data();

    boolean isConsumed();

    boolean isFailed();

    void consume();

    Handler<ServiceMessage<T>> messageHandler();
}