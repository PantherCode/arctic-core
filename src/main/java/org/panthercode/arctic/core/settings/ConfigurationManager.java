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

import java.util.*;

/**
 * The ConfigurationManager class handle a set of configuration associated by keys.
 * <p>
 * It's a Multiton design pattern implementation.
 *
 * @author PantherCode
 */
public class ConfigurationManager {

    /**
     * map with instances
     */
    private static Map<Object, Configuration> configurations = new HashMap<Object, Configuration>();

    /**
     * private constructor
     */
    private ConfigurationManager() {
    }

    /**
     * Returns the instance of configuration associated with key. This function is <tt>synchronized</tt>.
     *
     * @param key key of configuration instance.
     * @return Returns the instance of configuration associated with key.
     */
    public synchronized static Properties instance(final Object key) {
        if (!configurations.containsKey(key)) {
            configurations.put(key, new Configuration());
        }

        return configurations.get(key);
    }

    /**
     * Returns a collection of all configurations.
     *
     * @return Returns a collection of all configurations.
     */
    public static Collection<Configuration> configurations() {
        return configurations.values();
    }

    /**
     * Returns a collection of all keys.
     *
     * @return Returns a collection of all keys.
     */
    public static Set<Object> keys() {
        return configurations.keySet();
    }

    /**
     * Checks if the configuration manager contains the key.
     *
     * @param key key to check
     * @return Returns <tt>true</tt> if the manager contains the key; Otherwise <tt>false</tt>.
     */
    public static boolean containsKey(final Object key) {
        return configurations.containsKey(key);
    }
}
