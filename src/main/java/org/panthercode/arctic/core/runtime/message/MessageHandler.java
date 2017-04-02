package org.panthercode.arctic.core.runtime.message;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Handler;

/**
 * Created by architect on 02.04.17.
 */
public class MessageHandler<T> implements Handler<Message<T>> {

    private boolean ignoreExceptions = false;

    public MessageHandler() {
    }

    public MessageHandler(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    public boolean isIgnoreExceptions() {
        return this.ignoreExceptions;
    }

    public void ignoreExceptions(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    @Override
    public void handle(Message<T> message) {
        this.handle(message, null);
    }

    public void handle(Message<T> message, Handler<MessageResponse<T>> responseHandler) {
        this.handle(message, responseHandler, null);
    }

    public void handle(Message<T> message, Handler<MessageResponse<T>> responseHandler, Handler<Exception> exceptionHandler) {
        try {
            ArgumentUtils.checkNotNull(message, "message");

            MessageResponse<T> response = message.consume();

            if (responseHandler != null) {
                responseHandler.handle(response);
            }
        } catch (Exception e) {
            if (!this.ignoreExceptions) {
                if (exceptionHandler == null) {
                    throw new RuntimeException("While running the message handler an error is occurred.", e);
                } else {
                    try {
                        exceptionHandler.handle(e);
                    } catch (Exception ex) {
                        throw new RuntimeException("While running the exception handler an error is occurred.", ex);
                    }
                }
            }
        }
    }
}
