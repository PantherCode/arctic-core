package org.panthercode.arctic.core.event;

/**
 * Created by architect on 28.02.17.
 */
public abstract class EventArgs {

    private boolean isHandled = false;

    public EventArgs() {
    }

    public synchronized void isHandled(boolean isHandled) {
        this.isHandled = isHandled;
    }

    public boolean isHandled() {
        return this.isHandled;
    }
}
