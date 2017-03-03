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

    public <T> T get(Object key, Class<T> returnType){
        ArgumentUtils.checkNotNull(returnType, "return type");

        return returnType.cast(this.get(key));
    }

    public int getInteger(Object key) {
        return this.get(key, Integer.class);
    }

    public String getString(Object key) {
        return this.get(key, String.class);
    }

    public boolean getBoolean(Object key) {
        return this.get(key, Boolean.class);
    }

    public double getDouble(Object key) {
        return this.get(key, Double.class);
    }

    public byte getByte(Object key) {
        return this.get(key, Byte.class);
    }

    public float getFloat(Object key) {
        return this.get(key, Float.class);
    }

    public long getLong(Object key) {
        return this.get(key, Long.class);
    }

    public short getShort(Object key) {
        return this.get(key, Short.class);
    }

    public Character getCharater(Object key) {
        return this.get(key, Character.class);
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
            load(stream);
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
            store(stream, this.comment);
        }
    }
}
