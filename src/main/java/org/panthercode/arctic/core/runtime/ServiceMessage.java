package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.event.Handler;

/**
 * Created by architect on 26.03.17.
 */
public interface ServiceMessage<T> {
    /**
     *
     * @return
     */
    T data();

    /**
     *
     * @return
     */
    boolean isConsumed();

    /**
     *
     * @return
     */
    boolean isFailed();

    /**
     *
     */
    void consume();

    /**
     *
     * @return
     */
    Handler<ServiceMessage<T>> messageHandler();
}