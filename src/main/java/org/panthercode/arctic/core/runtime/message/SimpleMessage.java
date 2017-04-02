package org.panthercode.arctic.core.runtime.message;

/**
 * Created by architect on 02.04.17.
 */
public class SimpleMessage<T> implements Message<T> {

    private T body;

    public SimpleMessage() {
    }

    public SimpleMessage(T body) {
        this.body = body;
    }

    @Override
    public T body() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public MessageResponse<T> consume() {
        return MessageResponse.ok(this.body);
    }

    @Override
    public MessageResponse<T> fail(int code, String message) {
        return MessageResponse.fail(this.body, code, message);
    }
}
