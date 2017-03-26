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
import org.panthercode.arctic.core.collections.helper.DefaultAllocator;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;

import java.util.*;

/**
 * The <tt>VersionMap</tt> stores a set of objects with its version.
 *
 * @author PantherCode
 * @see Map
 * @see DefaultAllocator
 * @since 1.0
 */
public class VersionMap<K, V extends Versionable> implements Map<K, V> {

    /**
     * actual map to store objects
     */
    private final HashMap<K, TreeMap<Version, V>> map;

    /**
     * Default Constructor
     */
    public VersionMap() {
        this.map = new HashMap<>();
    }

    /**
     * Returns the actual number of elements the {@link Map} contains.
     *
     * @return Returns the actual number of elements the map contains.
     */
    @Override
    public int size() {
        int size = 0;

        for (K key : this.map.keySet()) {
            size += this.map.get(key).size();
        }


        return size;
    }

    /**
     * Returns a flag that indicates whether the map has no element or not.
     *
     * @return Returns <tt>true</tt> if size is zero; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /**
     * Returns a flag that indicates whether the map contains the given key or not.
     *
     * @param key value to check
     * @return Returns <tt>true</tt> if the map contains the given key; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    /**
     * Returns a flag that indicates whether the map contains the given value or not.
     *
     * @param value value to check
     * @return Returns <tt>true</tt> if the map contains the given value; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean containsValue(Object value) {
        for (Object key : this.keySet()) {
            if (this.map.get(key).containsValue(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a flag that indicates whether the map contains a given object with specific version or not.
     *
     * @param key     key of object
     * @param version version of object
     * @return Returns <tt>true</tt> if the map contain the given key; Otherwise <tt>false</tt>.
     */
    public boolean contains(Object key, Version version) {
        return this.map.containsKey(key) && this.map.get(key).containsKey(version);
    }

    /**
     * Returns the object with highest version number that is stored under given key.
     *
     * @param key value that represents the object in map
     * @return Returns the object with highest version number if the map contains the given key; Otherwise <tt>null</tt>.
     */
    @Override
    public V get(Object key) {
        if (this.map.containsKey(key)) {
            return this.map.get(key).lastEntry().getValue();
        }

        return null;
    }

    /**
     * Returns the obejct with specific version that is stored under given key.
     *
     * @param key     value that represents the object in map
     * @param version version of object
     * @return Returns the object with given version number if the map contains the key; Otherwise <tt>null</tt>.
     */
    public V get(Object key, Version version) {
        if (this.map.containsKey(key) && this.map.get(key).containsKey(version)) {
            return this.map.get(key).get(version);
        }

        return null;
    }

    /**
     * Stores a new value in the map. If the map doesn't contains an object with its version under given key, the object
     * will store at this position. Otherwise the old value will replaced with the new one.
     *
     * @param key   key that represents the value in the map
     * @param value value to store
     * @return Returns the old value or <tt>null</tt> if the map doesn't contain the object at given position.
     */
    @Override
    public synchronized V put(K key, V value) {
        ArgumentUtils.checkNotNull(key, "key");
        ArgumentUtils.checkNotNull(value, "value");

        TreeMap<Version, V> tmpMap = this.containsKey(key) ? this.map.get(key) : new TreeMap<>();

        V result = tmpMap.put(value.version(), value);

        this.map.put(key, tmpMap);

        return result;
    }

    /**
     * Removes the object with the highest version number.
     *
     * @param key key of stored value
     * @return Returns the removed object or <tt>null</tt> if the map doesn't contains the object.
     */
    @Override
    public synchronized V remove(Object key) {
        return this.containsKey(key) ? this.map.remove(key).lastEntry().getValue() : null;
    }

    /**
     *
     * @param key
     * @param version
     * @return
     */
    @Override
    public synchronized boolean remove(Object key, Object version) {
        if (!(version instanceof Version)) {
            throw new IllegalArgumentException("The class type of value is not Version.");
        }

        return this.removeValue(key, (Version) version) != null;
    }

    /**
     *
     * @param key
     * @param version
     * @return
     */
    public synchronized V removeValue(Object key, Version version) {
        if (this.map.containsKey(key) && this.map.get(key).containsKey(version)) {
            return this.map.get(key).remove(version);
        }

        return null;
    }

    /**
     * Adds a collection of elements to the map.
     *
     * @param m collection with objects
     */
    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        if (m != null) {
            V value;

            for (K key : m.keySet()) {
                if (key == null) {
                    continue;
                }

                value = m.get(key);

                if (value != null) {
                    this.put(key, m.get(key));
                }
            }
        }
    }

    /**
     * Removes all elements from the map.
     */
    @Override
    public synchronized void clear() {
        this.map.clear();
    }

    /**
     * Generate a set of all keys in the map.
     *
     * @return Returns a set of all keys.
     */
    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    /**
     * Returns a flatten collection of all elements in the map.
     *
     * @return Returns a flatten collection of all elements in the map.
     */
    @Override
    public Collection<V> values() {
        Collection<V> collection = new ArrayList<>(this.size());

        TreeMap<Version, V> tmpMap;

        for (K key : this.map.keySet()) {
            tmpMap = this.map.get(key);

            for (Version version : tmpMap.keySet()) {
                collection.add(tmpMap.get(version));
            }
        }

        return collection;
    }

    /**
     * Returns a flatten set of all elements in the map.
     *
     * @return Returns a flatten set of all elements in the map.
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();

        TreeMap<Version, V> tmpMap;

        for (K key : this.map.keySet()) {
            tmpMap = this.map.get(key);

            for (Version version : tmpMap.keySet()) {
                set.add(new AbstractMap.SimpleEntry<>(key, tmpMap.get(version)));
            }
        }

        return set;
    }
}