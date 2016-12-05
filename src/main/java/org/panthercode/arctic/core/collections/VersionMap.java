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
 * TODO: documentation
 *
 * @author PantherCode
 */
public class VersionMap<K, V extends Versionable> implements Map<K, V> {

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

    @Override
    public int size() {
        int size = 0;

        TreeMap<Version, V> tmpMap = null;

        for (K key : this.map.keySet()) {
            size += this.map.get(key).size();
        }


        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.map.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Object key : this.keySet()) {
            if (this.map.get(key).containsValue(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public V get(Object key) {
        if (this.map.containsKey(key)) {
            return this.map.get(key).lastEntry().getValue();
        }

        return null;
    }

    public V get(Object key, Version version) {
        if (this.map.containsKey(key) && this.map.get(key).containsKey(version)) {
            return this.map.get(key).get(version);
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        ArgumentUtils.assertNotNull(value, "value");

        TreeMap<Version, V> tmpMap = this.containsKey(key) ? this.map.get(key) : new TreeMap<Version, V>();

        V result = tmpMap.put(value.version(), value);

        this.map.put(key, tmpMap);

        return result;
    }

    @Override
    public V remove(Object key) {
        return this.map.remove(key).lastEntry().getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m != null) {
            for (K key : m.keySet()) {
                this.put(key, m.get(key));
            }
        }
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = Collections.EMPTY_LIST;

        TreeMap<Version, V> tmpMap = null;

        for (K key : this.map.keySet()) {
            tmpMap = this.map.get(key);

            for (Version version : tmpMap.keySet()) {
                collection.add(tmpMap.get(version));
            }
        }

        return collection;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = Collections.EMPTY_SET;

        TreeMap<Version, V> tmpMap = null;

        for (K key : this.map.keySet()) {
            tmpMap = this.map.get(key);

            for (Version version : tmpMap.keySet()) {
                set.add(new AbstractMap.SimpleEntry<K, V>(key, tmpMap.get(key)));
            }
        }

        return set;
    }
}
