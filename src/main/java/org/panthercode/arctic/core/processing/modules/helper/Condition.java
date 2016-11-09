package org.panthercode.arctic.core.processing.modules.helper;

/**
 * Created by architect on 15.10.16.
 */
public abstract class Condition<T> {

    private T value;

    public Condition(T defaultValue) {
        this.setValue(defaultValue);
    }

    public abstract boolean check(T actualValue);

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
