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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    /**
     *
     * @param key
     * @param returnType
     * @param <T>
     * @return
     * @throws NullPointerException
     */
    public <T> T get(Object key, Class<T> returnType)
            throws NullPointerException {
        return ArgumentUtils.checkNotNull(returnType, "type").cast(this.get(key));
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @param returnType
     * @param <T>
     * @return
     */
    public <T> T getOrDefault(Object key, Object defaultValue, Class<T> returnType) {
        ArgumentUtils.checkNotNull(returnType, "type");
        ArgumentUtils.checkNotNull(defaultValue, "default value");

        Object result = this.contains(key) ? this.get(key) : defaultValue;

        return returnType.cast(result);
    }

    /**
     *
     * @param key
     * @return
     */
    public int getInteger(Object key) {
        return this.get(key, Integer.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public int getIntegerOrDefault(Object key, int defaultValue) {
        return this.getOrDefault(key, defaultValue, Integer.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public String getString(Object key) {
        return this.get(key, String.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getStringOrDefault(Object key, String defaultValue) {
        return this.getOrDefault(key, defaultValue, String.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean getBoolean(Object key) {
        return this.get(key, Boolean.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBooleanOrDefault(Object key, boolean defaultValue) {
        return this.getOrDefault(key, defaultValue, Boolean.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public double getDouble(Object key) {
        return this.get(key, Double.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public double getDoubleOrDefault(Object key, double defaultValue) {
        return this.getOrDefault(key, defaultValue, Double.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public byte getByte(Object key) {
        return this.get(key, Byte.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public byte getByteOrDefault(Object key, byte defaultValue) {
        return this.getOrDefault(key, defaultValue, Byte.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public float getFloat(Object key) {
        return this.get(key, Float.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public float getFloatOrDefault(Object key, float defaultValue) {
        return this.getOrDefault(key, defaultValue, Float.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public long getLong(Object key) {
        return this.get(key, Long.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public long getLongOrDefault(Object key, long defaultValue) {
        return this.getOrDefault(key, defaultValue, Long.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public short getShort(Object key) {
        return this.get(key, Short.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public short getShortOrDefault(Object key, short defaultValue) {
        return this.getOrDefault(key, defaultValue, Short.class);
    }

    /**
     *
     * @param key
     * @return
     */
    public Character getCharater(Object key) {
        return this.get(key, Character.class);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Character getCharacterOrDefault(Object key, char defaultValue) {
        return this.getOrDefault(key, defaultValue, Character.class);
    }

    /**
     *
     * @param key1
     * @param key2
     * @return
     */
    public Object[] getAll(Object key1, Object key2) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param key1
     * @param key2
     * @param key3
     * @return
     */
    public Object[] getAll(Object key1, Object key2, Object key3) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param key1
     * @param key2
     * @param key3
     * @param rest
     * @return
     */
    public Object[] getAll(Object key1, Object key2, Object key3, Object... rest) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param other
     */
    public void merge(Context other) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param other
     * @param key
     */
    public void merge(Context other, Object key) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param other
     * @param key1
     * @param key2
     */
    public void merge(Context other, Object key1, Object key2) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param other
     * @param key1
     * @param key2
     * @param key3
     */
    public void merge(Context other, Object key1, Object key2, Object key3) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param other
     * @param key1
     * @param key2
     * @param key3
     * @param rest
     */
    public void merge(Context other, Object key1, Object key2, Object key3, Object... rest) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param key
     * @throws MissingKeyException
     */
    public void check(Object key)
            throws MissingKeyException {
        throw new NotImplementedException();
    }

    /**
     *
     * @param key1
     * @param key2
     * @throws MissingKeyException
     */
    public void check(Object key1, Object key2)
            throws MissingKeyException {
        throw new NotImplementedException();
    }

    /**
     *
     * @param key1
     * @param key2
     * @param key3
     * @throws MissingKeyException
     */
    public void check(Object key1, Object key2, Object key3)
            throws MissingKeyException {
        throw new NotImplementedException();
    }

    /**
     *
     * @param key1
     * @param key2
     * @param key3
     * @param rest
     * @throws MissingKeyException
     */
    public void check(Object key1, Object key2, Object key3, Object... rest)
            throws MissingKeyException {
        throw new NotImplementedException();
    }

    /**
     * @return
     */
    public static ContextBuilder create() {
        return new ContextBuilder();
    }

    /**
     *
     */
    public static class ContextBuilder {
        /**
         *
         */
        private Hashtable<Object, Object> table = new Hashtable<>();

        /**
         *
         * @param key
         * @param value
         * @return
         */
        public ContextBuilder append(Object key, Object value) {
            ArgumentUtils.checkNotNull(key, "key");

            this.table.put(key, value);

            return this;
        }

        /**
         *
         * @return
         */
        public Context build() {
            return new Context(this.table);
        }
    }
}
