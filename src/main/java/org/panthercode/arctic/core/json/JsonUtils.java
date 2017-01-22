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
