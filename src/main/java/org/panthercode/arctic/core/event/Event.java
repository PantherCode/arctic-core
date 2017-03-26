package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.runtime.Message;

import java.util.Set;

/**
 * Created by architect on 26.03.17.
 */
public interface Event<T extends EventArgs> {

    /**
     * @param handler
     * @return
     */
    boolean addHandler(EventHandler<T> handler);

    /**
     * @param handler
     * @return
     */
    boolean removeHandler(EventHandler<T> handler);

    /**
     * @param handler
     * @return
     */
    boolean hasHandler(EventHandler<T> handler);

    /**
     * @return
     */
    Set<EventHandler<T>> handlers();

    /**
     * @return
     */
    int size();

    /**
     * @return
     */
    boolean isEmpty();

    /**
     * @param source
     * @param eventArgs
     */
    void send(Object source, T eventArgs);

    /**
     * @param source
     * @param eventArgs
     * @param handler
     */
    void send(Object source, T eventArgs, Handler<Message<T>> handler);
}
