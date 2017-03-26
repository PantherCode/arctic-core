package org.panthercode.arctic.core.event;

/**
 * Created by architect on 26.03.17.
 */
public interface EventFactory {

    <T extends EventArgs> Event<T> create();
}
