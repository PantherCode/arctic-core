package org.panthercode.arctic.core.event.impl.arguments;

/**
 * Created by architect on 01.03.17.
 */
public class ValueChangedEventArgs extends AbstractEventArgs {

    /**
     *
     */
    private Object oldValue;

    /**
     *
     */
    private Object newValue;

    /**
     *
     * @param oldValue
     * @param newValue
     */
    public ValueChangedEventArgs(Object oldValue, Object newValue) {
        super();

        this.oldValue = oldValue;

        this.newValue = newValue;
    }

    /**
     *
     * @return
     */
    public Object oldValue() {
        return this.oldValue;
    }

    /**
     *
     * @return
     */
    public Object newValue() {
        return this.newValue;
    }
}
