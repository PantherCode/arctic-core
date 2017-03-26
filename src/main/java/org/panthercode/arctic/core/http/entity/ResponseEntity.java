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
    /**
     *
     */
    private boolean isSuccessful;

    /**
     *
     */
    private String message;

    /**
     *
     */
    public ResponseEntity() {
        this(null);
    }

    /**
     *
     * @param data
     */
    public ResponseEntity(T data) {
        this(data, true, "Successful");
    }

    /**
     *
     * @param data
     * @param isSuccessful
     * @param message
     */
    public ResponseEntity(T data, boolean isSuccessful, String message) {
        super(data);

        this.isSuccessful = isSuccessful;

        this.message = message == null ? "" : message;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return this.message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    /**
     *
     * @return
     */
    public boolean isSuccessful() {
        return this.isSuccessful;
    }

    /**
     *
     * @param isSuccessful
     */
    public void isSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> fromJson(String json, Class<ResponseEntity<T>> clazz) {
        return JsonUtils.fromJson(json, clazz);
    }

    /**
     *
     * @param json
     * @param clazz
     * @param gson
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> fromJson(String json, Class<ResponseEntity<T>> clazz, Gson gson) {
        return JsonUtils.fromJson(json, clazz);
    }

    /**
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> ResponseEntity<T> fromResponse(HttpResponse response, Class<ResponseEntity<T>> clazz)
            throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return ResponseEntity.fromJson(entity, clazz);
    }

    /**
     *
     * @param response
     * @param clazz
     * @param gson
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> ResponseEntity<T> fromResponse(HttpResponse response, Class<ResponseEntity<T>> clazz, Gson gson) throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return ResponseEntity.fromJson(entity, clazz, gson);
    }

    /**
     *
     * @return
     */
    public String toJson() {
        return JsonUtils.toJson(this);
    }

    /**
     *
     * @param gson
     * @return
     */
    public String toJson(Gson gson) {
        return JsonUtils.toJson(this, gson);
    }
}
