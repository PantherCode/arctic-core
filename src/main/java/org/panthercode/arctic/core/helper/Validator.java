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

    /**
     * @param value
     * @return
     * @throws IllegalArgumentException
     */
    default T validate(T value)
            throws IllegalArgumentException {
        if (this.accept(value)) {
            return value;
        }

        throw new IllegalArgumentException("The value is not correct.");
    }
}
