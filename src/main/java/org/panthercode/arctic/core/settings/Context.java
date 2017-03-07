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
package org.panthercode.arctic.core.settings;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * The context class is a Hashtable to store related items at runtime. The items are not stored persistently.
 *
 * @author PantherCode
 * @since 1.0
 */
public class Context extends Hashtable<Object, Object> {

    /**
     * Constructor
     */
    public Context() {
        super();
    }

    /**
     * Constructor
     *
     * @param capacity the initial capacity of hashtable
     */
    public Context(int capacity) {
        super(capacity);
    }

    /**
     * Constructor
     *
     * @param capacity   the initial capacity of hashtable
     * @param loadFactor the load factor of hashtable
     */
    public Context(int capacity, float loadFactor) {
        super(capacity, loadFactor);
    }

    /**
     * Constructor
     *
     * @param map other map whose content is mapped in this hashtable
     */
    public Context(Map<Object, Object> map) {
        super(map);
    }

    //TODO: throws Nullpoiner
    public <T> T get(Object key, Class<T> returnType) {
        ArgumentUtils.checkNotNull(returnType, "type");

        return returnType.cast(this.get(key));
    }

    public <T> T getOrDefault(Object key, Object defaultValue, Class<T> returnType) {
        ArgumentUtils.checkNotNull(returnType, "type");
        ArgumentUtils.checkNotNull(defaultValue, "default value");

        Object result = this.contains(key) ? this.get(key) : defaultValue;

        return returnType.cast(result);
    }

    public int getInteger(Object key) {
        return this.get(key, Integer.class);
    }

    public int getIntegerOrDefault(Object key, int defaultValue) {
        return this.getOrDefault(key, defaultValue, Integer.class);
    }

    public String getString(Object key) {
        return this.get(key, String.class);
    }

    public String getStringOrDefault(Object key, String defaultValue) {
        return this.getOrDefault(key, defaultValue, String.class);
    }

    public boolean getBoolean(Object key) {
        return this.get(key, Boolean.class);
    }

    public boolean getBooleanOrDefault(Object key, boolean defaultValue) {
        return this.getOrDefault(key, defaultValue, Boolean.class);
    }

    public double getDouble(Object key) {
        return this.get(key, Double.class);
    }

    public double getDoubleOrDefault(Object key, double defaultValue) {
        return this.getOrDefault(key, defaultValue, Double.class);
    }

    public byte getByte(Object key) {
        return this.get(key, Byte.class);
    }

    public byte getByteOrDefault(Object key, byte defaultValue) {
        return this.getOrDefault(key, defaultValue, Byte.class);
    }

    public float getFloat(Object key) {
        return this.get(key, Float.class);
    }

    public float getFloatOrDefault(Object key, float defaultValue) {
        return this.getOrDefault(key, defaultValue, Float.class);
    }

    public long getLong(Object key) {
        return this.get(key, Long.class);
    }

    public long getLongOrDefault(Object key, long defaultValue) {
        return this.getOrDefault(key, defaultValue, Long.class);
    }

    public short getShort(Object key) {
        return this.get(key, Short.class);
    }

    public short getShortOrDefault(Object key, short defaultValue) {
        return this.getOrDefault(key, defaultValue, Short.class);
    }

    public Character getCharater(Object key) {
        return this.get(key, Character.class);
    }

    public Character getCharacterOrDefault(Object key, char defaultValue) {
        return this.getOrDefault(key, defaultValue, Character.class);
    }

    /**
     * @return
     */
    public static ContextBuilder create() {
        return new ContextBuilder();
    }

    public static class ContextBuilder {
        private Hashtable<Object, Object> table = new Hashtable<>();

        public ContextBuilder append(Object key, Object value) {
            ArgumentUtils.checkNotNull(key, "key");

            this.table.put(key, value);

            return this;
        }

        public Context build() {
            return new Context(this.table);
        }
    }
}
