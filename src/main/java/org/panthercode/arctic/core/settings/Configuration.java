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

import org.apache.commons.io.FileUtils;
import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class to store related items persistently. This class make the read and write operation a little bit more comfortable
 * than <tt>Properties</tt> class do.
 *
 * @author PantherCode
 * @since 1.0
 */
public class Configuration extends Properties {

    /**
     * comment associated to this configuration
     */
    private String comment;

    /**
     * Standard Constructor
     */
    public Configuration() {
    }

    /**
     * Constructor
     *
     * @param comment comment associated to this configuration
     */
    public Configuration(String comment) {
        this.setComment(comment);
    }

    /**
     * Constructor
     *
     * @param defaults default properties value
     */
    public Configuration(Properties defaults) {
        super(defaults);
    }

    /**
     * Constructor
     *
     * @param defaults default properties value
     * @param comment  comment associated to this configuration
     */
    public Configuration(Properties defaults, String comment) {
        super(defaults);

        this.setComment(comment);
    }

    /**
     * @param key
     * @param returnType
     * @param <T>
     * @return
     * @throws NullPointerException
     */
    public <T> T get(Object key, Class<T> returnType)
            throws NullPointerException {
        ArgumentUtils.checkNotNull(returnType, "type");

        return returnType.cast(this.get(key));
    }

    /**
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
     * @param key
     * @return
     */
    public int getInteger(Object key) {
        return this.get(key, Integer.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public int getIntegerOrDefault(Object key, int defaultValue) {
        return this.getOrDefault(key, defaultValue, Integer.class);
    }

    /**
     * @param key
     * @return
     */
    public String getString(Object key) {
        return this.get(key, String.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public String getStringOrDefault(Object key, String defaultValue) {
        return this.getOrDefault(key, defaultValue, String.class);
    }

    /**
     * @param key
     * @return
     */
    public boolean getBoolean(Object key) {
        return this.get(key, Boolean.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBooleanOrDefault(Object key, boolean defaultValue) {
        return this.getOrDefault(key, defaultValue, Boolean.class);
    }

    /**
     * @param key
     * @return
     */
    public double getDouble(Object key) {
        return this.get(key, Double.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public double getDoubleOrDefault(Object key, double defaultValue) {
        return this.getOrDefault(key, defaultValue, Double.class);
    }

    /**
     * @param key
     * @return
     */
    public byte getByte(Object key) {
        return this.get(key, Byte.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public byte getByteOrDefault(Object key, byte defaultValue) {
        return this.getOrDefault(key, defaultValue, Byte.class);
    }

    /**
     * @param key
     * @return
     */
    public float getFloat(Object key) {
        return this.get(key, Float.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public float getFloatOrDefault(Object key, float defaultValue) {
        return this.getOrDefault(key, defaultValue, Float.class);
    }

    /**
     * @param key
     * @return
     */
    public long getLong(Object key) {
        return this.get(key, Long.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public long getLongOrDefault(Object key, long defaultValue) {
        return this.getOrDefault(key, defaultValue, Long.class);
    }

    /**
     * @param key
     * @return
     */
    public short getShort(Object key) {
        return this.get(key, Short.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public short getShortOrDefault(Object key, short defaultValue) {
        return this.getOrDefault(key, defaultValue, Short.class);
    }

    /**
     * @param key
     * @return
     */
    public Character getCharater(Object key) {
        return this.get(key, Character.class);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Character getCharacterOrDefault(Object key, char defaultValue) {
        return this.getOrDefault(key, defaultValue, Character.class);
    }

    public void check(Object key)
            throws MissingKeyException {
        if (!this.containsKey(key)) {
            throw new MissingKeyException("The key is not in the map. [key=" + key.toString() + "]");
        }
    }

    /**
     * Returns the comment for this configuration.
     *
     * @return Returns the comment of this configuration.
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Set the comment associated to this configuration.
     *
     * @param comment new comment
     */
    public void setComment(final String comment) {
        this.comment = comment == null ? "" : comment;
    }

    /**
     * Load the configuration from file.
     *
     * @param path path to file
     * @throws IOException Is thrown if an error occurs while reading file.
     */
    public void load(final String path) throws IOException {
        try (FileInputStream stream = FileUtils.openInputStream(new File(path))) {
            this.load(stream);
        }
    }

    /**
     * Store the configuration to file.
     *
     * @param path path to file
     * @throws IOException Is thrown if an error occurs while writing in file.
     */
    public void store(final String path) throws IOException {
        try (FileOutputStream stream = FileUtils.openOutputStream(new File(path))) {
            this.store(stream, this.comment);
        }
    }

    /**
     * @return
     */
    public static ConfigurationBuilder create() {
        return new ConfigurationBuilder();
    }

    /**
     *
     */
    public static class ConfigurationBuilder {
        /**
         *
         */
        private Properties properties = new Properties();

        /**
         * @param key
         * @param value
         * @return
         */
        public ConfigurationBuilder append(Object key, Object value) {
            ArgumentUtils.checkNotNull(key, "key");

            this.properties.put(key, value);

            return this;
        }

        /**
         * @return
         */
        public Configuration build() {
            return new Configuration(this.properties);
        }
    }
}
