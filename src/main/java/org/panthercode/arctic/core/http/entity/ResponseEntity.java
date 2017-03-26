package org.panthercode.arctic.core.http.entity;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.json.JsonUtils;

import java.io.IOException;

/**
 * Created by architect on 26.03.17.
 */
public class ResponseEntity<T> extends AbstractEntity<T> {

    private boolean isSuccessful;

    private String message;

    public ResponseEntity() {
        this(null);
    }

    public ResponseEntity(T data) {
        this(data, true, "Successful");
    }

    public ResponseEntity(T data, boolean isSuccessful, String message) {
        super(data);

        this.isSuccessful = isSuccessful;

        this.message = message == null ? "" : message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    public boolean isSuccessful() {
        return this.isSuccessful;
    }

    public void isSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public static <T> ResponseEntity<T> fromJson(String json, Class<ResponseEntity<T>> clazz) {
        return JsonUtils.fromJson(json, clazz);
    }

    public static <T> ResponseEntity<T> fromJson(String json, Class<ResponseEntity<T>> clazz, Gson gson) {
        return JsonUtils.fromJson(json, clazz);
    }

    public static <T> ResponseEntity<T> fromResponse(HttpResponse response, Class<ResponseEntity<T>> clazz)
            throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return ResponseEntity.fromJson(entity, clazz);
    }

    public static <T> ResponseEntity<T> fromResponse(HttpResponse response, Class<ResponseEntity<T>> clazz, Gson gson) throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return ResponseEntity.fromJson(entity, clazz, gson);
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    public String toJson(Gson gson) {
        return JsonUtils.toJson(this, gson);
    }
}
