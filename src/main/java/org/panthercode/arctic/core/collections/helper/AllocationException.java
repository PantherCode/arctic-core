package org.panthercode.arctic.core.collections.helper;

/**
 * Created by architect on 04.03.17.
 */
public class AllocationException extends Exception {

    public AllocationException() {
        super();
    }

    public AllocationException(Throwable e) {
        super(e);
    }

    public AllocationException(String message) {
        super(message);
    }

    public AllocationException(String message, Throwable e) {
        super(message, e);
    }
}
