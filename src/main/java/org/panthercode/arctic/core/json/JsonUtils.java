package org.panthercode.arctic.core.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * TODO: implementation
 *
 * @author PantherCode
 */
public class JsonUtils {

    private static final Gson gson = JsonUtils.create();

    private JsonUtils() {
    }

    public static Gson create() {
        return new Gson();
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    //TODO: implement null check
    public static JsonReader toReader(String json) {
        return new JsonReader(new StringReader(json));
    }

    public static JsonReader toReader(InputStream stream) {
        return new JsonReader(new InputStreamReader(stream));
    }

    public static JsonWriter toWriter(Writer writer) {
        return new JsonWriter(writer);
    }
}
