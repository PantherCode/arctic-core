package org.panthercode.arctic.core.event;

/**
 * Created by architect on 26.03.17.
 */
public interface EventArgs {

    /**
     *
     * @return
     */
    boolean isHandled();

    /**
     *
     * @param value
     */
    void isHandled(boolean value);
}
