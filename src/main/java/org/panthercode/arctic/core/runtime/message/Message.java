package org.panthercode.arctic.core.runtime.message;

/**
 * Created by architect on 26.03.17.
 */
public interface Message<T> {
    /**
     * @return
     */
    T body();

    /**
     *
     */
    MessageResponse<T> consume();

    /**
     *
     */
    MessageResponse<T> fail(int code, String message);
}