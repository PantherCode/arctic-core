package org.panthercode.arctic.core.settings;

/**
 * Created by architect on 25.03.17.
 */
public class MissingKeyException extends RuntimeException {

    /**
     *
     */
    public MissingKeyException() {
        super();
    }

    /**
     *
     * @param message
     */
    public MissingKeyException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public MissingKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public MissingKeyException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected MissingKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
