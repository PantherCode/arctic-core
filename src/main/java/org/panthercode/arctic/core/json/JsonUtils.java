/*
 * Copyright 2016 PantherCode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.panthercode.arctic.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Utility class with various functions to handle Json objects.
 *
 * @author PantherCode
 * @since 1.0
 */
public class JsonUtils {

    /**
     * instance of <tt>Gson</tt> object to transform json string to an object and vice versa
     */
    private static final Gson gson = JsonUtils.create();

    /**
     * Private Constructor
     */
    private JsonUtils() {
    }

    /**
     * Creates a new instance of <tt>Gson</tt> class.
     *
     * @return Returns a new instance of <tt>Gson</tt> class.
     */
    public static Gson create() {
        return new Gson();
    }

    /**
     *
     * @return
     */
    public static GsonBuilder builder() {
        return new GsonBuilder();
    }

    /**
     * Converts a json string to an object.
     *
     * @param json string representation of the object
     * @param type type of returned object
     * @param <T>  generic class type
     * @return Returns the converted object of corresponding json string.
     */
    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    /**
     *
     * @param json
     * @param type
     * @param gson
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type, Gson gson) {
        ArgumentUtils.checkNotNull(gson, "gson");

        return gson.fromJson(json, type);
    }

    /**
     * Converts a json string to an object.
     *
     * @param json  string representation of the object
     * @param clazz class type of returned object
     * @param <T>   generic class type
     * @return Returns the converted object of corresponding json string.
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     *
     * @param json
     * @param clazz
     * @param gson
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz, Gson gson) {
        ArgumentUtils.checkNotNull(gson, "gson");

        return gson.fromJson(json, clazz);
    }

    /**
     * Converts an object to its json string representation.
     *
     * @param object object to convert
     * @return Returns a json string representation of the object.
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     *
     * @param object
     * @param gson
     * @return
     */
    public static String toJson(Object object, Gson gson) {
        ArgumentUtils.checkNotNull(gson, "gson");

        return gson.toJson(object);
    }

    /**
     * Creates a new <tt>JsonReader</tt> from a json string.
     *
     * @param json json string
     * @return Returns a new instance of <tt>JsonReader</tt> class.
     */
    public static JsonReader toReader(String json) {
        return new JsonReader(new StringReader(json));
    }

    /**
     * Creates a new <tt>JsonReader</tt> from an <tt>InputStream</tt> object.
     *
     * @param stream object to read
     * @return Returns a new instance of <tt>JsonReader</tt> class.
     */
    public static JsonReader toReader(InputStream stream) {
        return new JsonReader(new InputStreamReader(stream));
    }

    /**
     * Creates a new <tt>JsonWriter</tt> from an <tt>Writer</tt> object.
     *
     * @param writer object for writing
     * @return Returns a new instance of <tt>JsonWriter</tt>.
     */
    public static JsonWriter toWriter(Writer writer) {
        return new JsonWriter(writer);
    }
}
