package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.event.impl.DefaultEvent;

import java.util.Set;

/**
 * Created by architect on 26.03.17.
 */
public interface Event<T extends EventArgs> {

    /**
     *
     * @param handler
     * @return
     */
    boolean addHandler(EventHandler<T> handler);

    /**
     *
     * @param handler
     * @return
     */
    boolean removeHandler(EventHandler<T> handler);

    /**
     *
     * @param handler
     * @return
     */
    boolean hasHandler(EventHandler<T> handler);

    /**
     *
     * @return
     */
    Set<EventHandler<T>> handlerSet();

    /**
     *
     * @return
     */
    int size();

    /**
     *
     * @return
     */
    boolean isEmpty();

    /**
     *
     * @param source
     * @param args
     */
    void raise(Object source, T args);

    /**
     *
     * @param source
     * @param args
     * @param handler
     */
    void raise(Object source, T args, Handler<EventMessage<T>> handler);
}
