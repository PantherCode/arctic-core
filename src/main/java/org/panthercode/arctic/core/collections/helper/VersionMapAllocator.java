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
package org.panthercode.arctic.core.collections.helper;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.collections.VersionMap;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;

/**
 * The <tt>VersionMapAllocator</tt> class is used to control the access to a <tt>VersionMap</tt> object.
 *
 * @author PantherCode
 * @see VersionMap
 * @since 1.0
 */
public class VersionMapAllocator<K, V extends Versionable> {

    /**
     * actual instance of VersionMap
     */
    private VersionMap<K, V> versionMap;

    /**
     * Constructor
     *
     * @param map actual <tt>VersionMap</tt> object
     */
    public VersionMapAllocator(VersionMap<K, V> map) {
        ArgumentUtils.checkNotNull(map, "map");

        this.versionMap = map;
    }

    /**
     * Returns a flag that indicates whether the map contains the given key or not.
     *
     * @param key key to check
     * @return Returns <tt>true</tt> if the map contains the key; Otherwise <tt>false</tt>.
     */
    public boolean contains(Object key) {
        ArgumentUtils.checkNotNull(key, "key");

        return this.versionMap.containsKey(key);
    }

    /**
     * Returns a flaf that indicates whether the map contains a given object with a specific version or not.
     *
     * @param key     key to check
     * @param version version of searched object
     * @return Returns <tt>true</tt> if the map contains the object; Otherwise <tt>false</tt>.
     */
    public boolean contains(Object key, Version version) {
        ArgumentUtils.checkNotNull(key, "key");
        ArgumentUtils.checkNotNull(version, "version");

        return this.versionMap.contains(key, version);
    }

    /**
     * Returns the object which is represented by given key.
     *
     * @param key key of object
     * @return Returns the object if the map contains it; Otherwise <tt>null</tt>.
     */
    public V allocate(Object key) {
        ArgumentUtils.checkNotNull(key, "key");

        return this.versionMap.get(key);
    }

    /**
     * Returns the object with specific version which is represented by given key.
     *
     * @param key     key of object
     * @param version version of object
     * @return Returns the object if the map contains it; Otherwise <tt>false</tt>.
     */
    public V allocate(Object key, Version version) {
        ArgumentUtils.checkNotNull(key, "key");
        ArgumentUtils.checkNotNull(version, "version");

        return this.versionMap.get(key, version);
    }
}
