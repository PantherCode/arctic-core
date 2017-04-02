package org.panthercode.arctic.core.http.entity;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.json.JsonUtils;

import java.io.IOException;

/**
 * Created by architect on 26.03.17.
 * JsonEntity
 */
public class JsonEntity<T> extends AbstractEntity<T> {
    /**
     *
     */
    public JsonEntity() {
        super();
    }

    /**
     * @param data
     */
    public JsonEntity(T data) {
        super(data);
    }

    /**
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> JsonEntity<T> fromJson(String json, Class<JsonEntity<T>> clazz) {
        return JsonUtils.fromJson(json, clazz);
    }

    /**
     * @param json
     * @param clazz
     * @param gson
     * @param <T>
     * @return
     */
    public static <T> JsonEntity<T> fromJson(String json, Class<JsonEntity<T>> clazz, Gson gson) {
        return JsonUtils.fromJson(json, clazz);
    }

    /**
     * @param response
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> JsonEntity<T> fromResponse(HttpResponse response, Class<JsonEntity<T>> clazz)
            throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return JsonEntity.fromJson(entity, clazz);
    }

    /**
     * @param response
     * @param clazz
     * @param gson
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> JsonEntity<T> fromResponse(HttpResponse response, Class<JsonEntity<T>> clazz, Gson gson)
            throws IOException {
        ArgumentUtils.checkNotNull(response, "response");

        String entity = EntityUtils.toString(response.getEntity());

        return JsonEntity.fromJson(entity, clazz, gson);
    }

    /**
     * @return
     */
    public String toJson() {
        return JsonUtils.toJson(this);
    }

    /**
     * @param gson
     * @return
     */
    public String toJson(Gson gson) {
        return JsonUtils.toJson(this, gson);
    }
}
