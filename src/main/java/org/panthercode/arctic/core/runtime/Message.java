package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.event.Handler;

/**
 * Created by architect on 26.03.17.
 */
public interface Message<T> {
    /**
     * @return
     */
    T content();

    /**
     * @return
     */
    boolean isConsumed();

    /**
     * @return
     */
    boolean isFailed();

    /**
     *
     */
    void consume();

    /**
     * @return
     */
    Handler<Message<T>> consumedHandler();

    /**
     * @return
     */
    Handler<MessageConsumeFailure<T>> exceptionHandler();
}