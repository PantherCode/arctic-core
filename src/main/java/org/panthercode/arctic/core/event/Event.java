package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.event.impl.DefaultEvent;

import java.util.Set;

/**
 * Created by architect on 26.03.17.
 */
public interface Event<T extends EventArgs> {

    boolean addHandler(EventHandler<T> handler);

    boolean removeHandler(EventHandler<T> handler);

    boolean hasHandler(EventHandler<T> handler);

    Set<EventHandler<T>> handlerSet();

    int size();

    boolean isEmpty();

    void raise(Object source, T args);

    void raise(Object source, T args, Handler<EventMessage<T>> handler);
}
