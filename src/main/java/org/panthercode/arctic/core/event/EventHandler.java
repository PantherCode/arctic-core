package org.panthercode.arctic.core.event;

/**
 * Created by architect on 28.02.17.
 */
public interface EventHandler<T extends EventArgs> {

    /**
     *
     * @param source
     * @param e
     */
    void handle(Object source, T e);
}
