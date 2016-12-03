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
package org.panthercode.arctic.core.settings.context;

import java.util.Hashtable;
import java.util.Map;

/**
 * The context class is a Hashtable to store related items at runtime. The items are not stored persistently.
 *
 * @author PantherCode
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
    public Context(Map<? extends String, ? extends Object> map) {
        super(map);
    }

    /**
     * Returns the value associated by key in its original class type.
     *
     * @param key key associated with value
     * @param <T> generic type of value
     * @return Returne the value associated by key if hashtable contains key; Otherwise <tt>null</tt>.
     */
    public <T> T gets(final Object key) {
        /*if (this.containsKey(key)) {
            return ClassUtils.cast(super.get(key));
        }*/

        return null;
    }
}
