package org.panthercode.arctic.core.event.impl.arguments;

import org.panthercode.arctic.core.event.EventArgs;

/**
 * Created by architect on 28.02.17.
 */
public abstract class AbstractEventArgs implements EventArgs {

    private boolean isHandled = false;

    public AbstractEventArgs() {
    }

    public synchronized void isHandled(boolean isHandled) {
        this.isHandled = isHandled;
    }

    public boolean isHandled() {
        return this.isHandled;
    }
}
