package org.panthercode.arctic.core.processing.modules.helper;

/**
 * Created by architect on 15.10.16.
 */
public abstract class Controller<T> {

    public abstract void reset();

    public abstract T value();

    public abstract void update();

    public abstract boolean accept();
}
