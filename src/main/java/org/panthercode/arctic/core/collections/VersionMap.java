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

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * TODO: class documentation
 *
 * @author PantherCode
 */
public class VersionMap<K, V extends Versionable> {

    private HashMap<K, TreeMap<Version, V>> map = new HashMap<K, TreeMap<Version, V>>();

    public VersionMap() {
        super();
    }

    public V get(K key) {
        ArgumentUtils.assertNotNull(key, "key");

        if (this.contains(key)) {
            return this.map.get(key).lastEntry().getValue();
        }

        return null;
    }

    public V get(K key, Version version) {
        ArgumentUtils.assertNotNull(key, "key");
        ArgumentUtils.assertNotNull(version, "version");

        if (this.contains(key)) {
            this.map.get(key).get(version);
        }

        return null;
    }

    public void put(K key, V value) {
        ArgumentUtils.assertNotNull(key, "key");
        ArgumentUtils.assertNotNull(value, "value");

        TreeMap<Version, V> treeMap = (this.map.containsKey(key)) ? this.map.get(key) : new TreeMap<Version, V>();

        treeMap.put(value.version(), value);

        this.map.put(key, treeMap);
    }

    public Set<Version> versions(K key) {
        ArgumentUtils.assertNotNull(key, "key");

        if (this.contains(key)) {
            return this.map.get(key).keySet();
        }

        return Collections.EMPTY_SET;
    }

    public void remove(K key) {
        if (this.contains(key)) {
            this.map.remove(key);
        }
    }

    public void remove(K key, Version version) {
        if (this.contains(key, version)) {
            this.map.get(key).remove(version);
        }
    }

    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    public boolean contains(K key, Version version) {
        return this.contains(key) && this.map.get(key).containsKey(version);
    }
}
