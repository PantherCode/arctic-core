package org.panthercode.arctic.core.collections.helper;

/**
 * Created by architect on 04.03.17.
 */
public class AllocationException extends Exception {

    /**
     *
     */
    public AllocationException() {
        super();
    }

    /**
     * @param message
     */
    public AllocationException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public AllocationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public AllocationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected AllocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
