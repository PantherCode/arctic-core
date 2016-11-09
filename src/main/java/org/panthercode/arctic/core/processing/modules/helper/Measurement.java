package org.panthercode.arctic.core.processing.modules.helper;

/**
 * Created by architect on 15.10.16.
 */
public abstract class Measurement<T> {

    private T defaultValue;

    private T latestValue;

    public Measurement(T defaultValue) {
        this.setDefaultValue(defaultValue);
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(T value) {
        this.defaultValue = value;
    }

    public void reset() {
        this.latestValue = defaultValue;
    }

    public T last() {
        return this.latestValue;
    }

    public void update() {
        this.latestValue = this.measure();
    }

    public boolean updateAndCheck() {
        this.update();

        return this.check();
    }

    public abstract boolean check();

    protected abstract T measure();
}
