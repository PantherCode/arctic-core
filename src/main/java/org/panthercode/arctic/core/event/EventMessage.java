package org.panthercode.arctic.core.event;

import org.panthercode.arctic.core.runtime.ServiceMessage;

/**
 * Created by architect on 26.03.17.
 */
public interface EventMessage<T extends EventArgs> extends ServiceMessage {

    Object source();

    EventHandler<T> eventHandler();
}
