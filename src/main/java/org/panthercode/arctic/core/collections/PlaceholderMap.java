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
package org.panthercode.arctic.core.collections;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.util.HashMap;

/**
 * @author PantherCode
 */
public class PlaceholderMap extends HashMap<Object, String> {

    /**
     *
     */
    private String placeholderRegex = "";

    /**
     *
     */
    private String value = "";

    /**
     * @param value
     * @param placeholderRegex
     * @throws NullPointerException
     */
    public PlaceholderMap(String value,
                          String placeholderRegex)
            throws NullPointerException {
        super();

        this.setPlaceholder(placeholderRegex);
        this.setValue(value);
    }

    //Todo: constructors

    /**
     * @return
     */
    public String getPlaceholer() {
        return this.placeholderRegex;
    }

    /**
     * @param placeholderRegex
     * @throws NullPointerException
     */
    public void setPlaceholder(String placeholderRegex)
            throws NullPointerException {
        ArgumentUtils.assertNotNull(placeholderRegex, "placeholder");

        this.placeholderRegex = placeholderRegex;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * @param valueRegex
     * @throws NullPointerException
     */
    public void setValue(String valueRegex)
            throws NullPointerException {
        ArgumentUtils.assertNotNull(this.value, "value");

        this.value = valueRegex;
    }

    /**
     * @param key
     * @return
     * @throws NullPointerException
     */
    @Override
    public String get(Object key)
            throws NullPointerException {
        return this.get(key, this.value);
    }

    /**
     * @param key
     * @param value
     * @return
     * @throws NullPointerException
     */
    public String get(Object key, String value)
            throws NullPointerException {
        return this.get(key, value, this.placeholderRegex);
    }

    /**
     * @param key
     * @param value
     * @param placeholderRegex
     * @return
     * @throws NullPointerException
     */
    public String get(Object key, String value, String placeholderRegex)
            throws NullPointerException {
        ArgumentUtils.assertNotNull(value, "value");
        ArgumentUtils.assertNotNull(placeholderRegex, "placeholer");

        String result = super.get(key);

        //replaces only the first finding of version
        return result == null ? result : result.replaceAll(placeholderRegex, value);
    }
}
