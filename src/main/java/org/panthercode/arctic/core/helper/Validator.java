package org.panthercode.arctic.core.helper;

/**
 * Created by architect on 26.03.17.
 */
public interface Validator<T> {

    /**
     * @param value
     * @return
     */
    boolean accept(T value);

    default T validate(T value, String message)
            throws IllegalArgumentException {
        if (this.accept(value)) {
            return value;
        }

        throw new IllegalArgumentException(message);
    }
}
