package org.panthercode.arctic.core.event.arguments;

import org.panthercode.arctic.core.event.EventArgs;

/**
 * Created by architect on 01.03.17.
 */
public class ValueChangedEventArgs extends EventArgs {

    private Object oldValue;

    private Object newValue;

    public ValueChangedEventArgs(Object oldValue, Object newValue) {
        this.oldValue = oldValue;

        this.newValue = newValue;
    }

    public Object oldValue() {
        return this.oldValue;
    }

    public Object newValue() {
        return this.newValue;
    }
}
