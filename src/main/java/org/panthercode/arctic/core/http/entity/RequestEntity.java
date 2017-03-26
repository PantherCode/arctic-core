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
public class RequestEntity<T> extends AbstractEntity<T> {

    public RequestEntity() {
        super();
    }

    public RequestEntity(T data) {
        super(data);
    }

    public static <T> RequestEntity<T> fromJson(String json, Class<RequestEntity<T>> clazz) {
        return JsonUtils.fromJson(json, clazz);
    }

    public static <T> RequestEntity<T> fromJson(String json, Class<RequestEntity<T>> clazz, Gson gson) {
        return JsonUtils.fromJson(json, clazz);
    }

    public static <T> RequestEntity<T> fromResponse(HttpResponse response, Class<RequestEntity<T>> clazz)
            throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return RequestEntity.fromJson(entity, clazz);
    }

    public static <T> RequestEntity<T> fromResponse(HttpResponse response, Class<RequestEntity<T>> clazz, Gson gson)
            throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return RequestEntity.fromJson(entity, clazz, gson);
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    public String toJson(Gson gson) {
        return JsonUtils.toJson(this, gson);
    }
}
