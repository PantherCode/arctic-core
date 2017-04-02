package org.panthercode.arctic.core.runtime.message;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

/**
 * Created by architect on 02.04.17.
 */
public class MessageResponse<T> {

    private static int STATUS_CODE_OK = 200;

    private static String STATUS_CODE_OK_MESSAGE = "OK";

    private T body;

    private int statusCode;

    private String statusLine;

    private boolean isConsumed;

    private boolean isFailed;

    public MessageResponse(int statusCode, String statusLine, T body, boolean isConsumed, boolean isFailed) {
        this.body = ArgumentUtils.checkNotNull(body, "body");
        this.statusCode = ArgumentUtils.checkGreaterOrEqualsZero(statusCode, "status code");
        this.statusLine = ArgumentUtils.checkNotNull(statusLine, "status line");
        this.isConsumed = isConsumed;
        this.isFailed = isFailed;
    }

    public static <T> MessageResponse<T> ok(T body) {
        return MessageResponse.ok(body, STATUS_CODE_OK_MESSAGE);
    }

    public static <T> MessageResponse<T> ok(T body, String message) {
        return new MessageResponse<T>(STATUS_CODE_OK, message, body, true, false);
    }

    public static <T> MessageResponse<T> fail(T body, int statusCode, String message) {
        return new MessageResponse<T>(statusCode, message, body, false, true);
    }

    public T content() {
        return this.body;
    }

    public int statusCode() {
        return this.statusCode;
    }

    public String statusLine() {
        return this.statusLine;
    }

    public boolean isConsumed() {
        return this.isConsumed;
    }

    public boolean isFailed() {
        return this.isFailed;
    }
}
