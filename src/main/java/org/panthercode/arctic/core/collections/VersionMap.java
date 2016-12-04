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
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;

import java.util.*;

/**
 * TODO: class documentation
 *
 * @author PantherCode
 */
public class VersionMap<K, V extends Versionable> implements Map<K, V>{

    /**
     * actual map to store objects
     */
    private HashMap<K, TreeMap<Version, V>> map = new HashMap<K, TreeMap<Version, V>>();

    /**
     * Default Constructor
     */
    public VersionMap() {
        super();
    }

    /**
     * Returns the stored value associated with the given key.
     *
     * @param key key that is associated with stored value
     * @return Returns the value associated to given key or <tt>null</tt> if the map doesn't contain the key.
     * @throws NullPointerException Is thrown if the value of key is <tt>null</tt>.
     */
    public V get(Object key)
            throws NullPointerException {
        ArgumentUtils.assertNotNull(key, "key");

        if (this.containsKey(key)) {
            return this.map.get(key).lastEntry().getValue();
        }

        return null;
    }

    /**
     * Returns the stored value associated with the given key and version.
     *
     * @param key     key that is associated with stored value
     * @param version version that is associated with stored value
     * @return Returns the value associated to given key and version or <tt>null</tt> if the map doesn't contain the element.
     */
    public V get(K key, Version version) {
        ArgumentUtils.assertNotNull(key, "key");
        ArgumentUtils.assertNotNull(version, "version");

        if (this.contains(key)) {
            this.map.get(key).get(version);
        }

        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }



    /**
     * Store an given
     *
     * @param key
     * @param value
     */
    @Override
    public V put(K key, V value) {
        ArgumentUtils.assertNotNull(key, "key");
        ArgumentUtils.assertNotNull(value, "value");

        TreeMap<Version, V> treeMap = (this.map.containsKey(key)) ? this.map.get(key) : new TreeMap<Version, V>();

        V result = treeMap.put(value.version(), value);

        this.map.put(key, treeMap);

        return result;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    /**
     * @param key
     * @return
     */
    public Set<Version> versions(K key) {
        ArgumentUtils.assertNotNull(key, "key");

        if (this.contains(key)) {
            return this.map.get(key).keySet();
        }

        return Collections.EMPTY_SET;
    }

    /**
     * @param key
     * @param version
     */
    public void remove(K key, Version version) {
        if (this.contains(key, version)) {
            this.map.get(key).remove(version);
        }
    }

    /**
     * @param key
     * @return
     */
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    /**
     * @param key
     * @param version
     * @return
     */
    public boolean contains(K key, Version version) {
        return this.contains(key) && this.map.get(key).containsKey(version);
    }
}
