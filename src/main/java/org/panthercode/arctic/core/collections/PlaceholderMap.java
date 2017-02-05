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
 * The <tt>PlaceholderMap</tt> is an extension of standard <tt>HashMap</tt> from <tt>java.collections</tt> package.
 * It's used to store strings with special patterns to decide at runtime which meaning is most fitting. Such a behavoir
 * can be helpful to handle a set of paths or other things containing names, versions, etc. .
 * <p>
 * <pre>
 * PlaceholderMap map = new PlaceholderMap("#version#", "1.0");
 *
 * map.put(1, "/path/to/resource/#version#/");
 *
 * System.out.println(map.get(1));       // prints /path/to/resource/1.0/
 * System.out.println(map.get(1, "2.0"); // prints /path/to/resource/2.0/
 * </pre>
 *
 * @author PantherCode
 * @see HashMap
 * @since 1.0
 */
public class PlaceholderMap extends HashMap<Object, String> {

    /**
     * value that should be replaced
     */
    private String placeholder = "";

    /**
     * value to replace the placeholder
     */
    private String value = "";

    /**
     * Constructor
     *
     * @param value       value for replacing
     * @param placeholder value that should replaced
     * @throws NullPointerException Is thrown if one of the parameters is <tt>null</tt>.
     */
    public PlaceholderMap(String placeholder,
                          String value)
            throws NullPointerException {
        super();

        this.setPlaceholder(placeholder);
        this.setValue(value);
    }


    /**
     * Returns the actual placeholder.
     *
     * @return Returns the actual placeholder.
     */
    public String getPlaceholder() {
        return this.placeholder;
    }

    /**
     * Sets the actual placeholder.
     *
     * @param placeholder value that should be replaced
     * @throws NullPointerException Is thrown if the value is <tt>null</tt>.
     */
    public void setPlaceholder(String placeholder)
            throws NullPointerException {
        ArgumentUtils.checkNotNull(placeholder, "placeholder");

        this.placeholder = placeholder;
    }

    /**
     * Returns the actual default value that is used for replacing.
     *
     * @return Returns the actual default value that is used for replacing.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the actual value that is used for replacing.
     *
     * @param value value that is used for replacing
     * @throws NullPointerException Is thrown if the parameter is <tt>null</tt>.
     */
    public void setValue(String value)
            throws NullPointerException {
        ArgumentUtils.checkNotNull(this.value, "value");

        this.value = value;
    }

    /**
     * Returns the corresponding value that is stored under the given key in the map and replaces the placeholder with
     * the actual value.
     *
     * @param key key that represents the stored value
     * @return Returns the corresponding value to the key or <tt>null</tt> if the key doesn't exists.
     * @throws NullPointerException Is thrown if the key is <tt>null</tt>.
     */
    @Override
    public String get(Object key)
            throws NullPointerException {
        return this.get(key, this.value);
    }

    /**
     * Returns the corresponding value that is stored under the given key in the map and replaces the placeholder with
     * the given value.
     *
     * @param key   key that represents the stored value
     * @param value value that is used for replacing instead of default value
     * @return Returns the corresponding value to the key or <tt>null</tt> if the key doesn't exists.
     * @throws NullPointerException Is thrown if one of the parameters is <tt>null</tt>.
     */
    public String get(Object key, String value)
            throws NullPointerException {
        return this.get(key, this.placeholder, value);
    }

    /**
     * Returns the corresponding value that is stored under the given key in the map and replaces the placeholder with
     * the given value.
     *
     * @param key         key that represents the stored value
     * @param value       value that is used for replacing instead of default value
     * @param placeholder placeholder that is used instead of the default one
     * @return Returns the corresponding value to the key or <tt>null</tt> if the key doesn't exists.
     * @throws NullPointerException Is thrown if one of the parameters is <tt>null</tt>
     */
    public String get(Object key, String placeholder, String value)
            throws NullPointerException {
        ArgumentUtils.checkNotNull(value, "value");
        ArgumentUtils.checkNotNull(placeholder, "placeholder");

        String result = super.get(key);

        return (result == null) ? null : result.replace(placeholder, value);
    }
}
