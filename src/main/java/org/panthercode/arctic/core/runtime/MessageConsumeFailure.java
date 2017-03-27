package org.panthercode.arctic.core.runtime;

/**
 * Created by architect on 27.03.17.
 */
public class MessageConsumeFailure<T> {

    private Message<T> message;

    private Throwable throwable;

    public MessageConsumeFailure(Message<T> message, Throwable throwable) {
        this.message = message;

        this.throwable = throwable;
    }

    public Message<T> getMessage() {
        return this.message;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}
