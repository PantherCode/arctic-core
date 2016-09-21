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
package org.panthercode.arctic.core.repository;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DefaultRepository<T extends Identifiable> implements Repository<T> {
    /**
     *
     */
    private final Identity identity;

    /**
     *
     */
    private Map<String, T> map = new HashMap<>();

    /**
     * @param identity
     */
    public DefaultRepository(Identity identity) {
        ArgumentUtils.assertNotNull(identity, "identity");

        this.identity = identity;
    }

    /**
     * @return
     */
    @Override
    public Identity identity() {
        return this.identity.clone();
    }

    /**
     * @param element
     */
    @Override
    public synchronized void store(T element) {
        if (element != null) {
            if (element.identity() != null) {
                this.map.put(element.identity().getName(), element);
            }
        }
    }

    /**
     * @param key
     */
    @Override
    public synchronized void delete(String key) {
        if (key != null) {
            this.map.remove(key);
        }
    }

    /**
     * @param key
     * @return
     */
    @Override
    public boolean contains(String key) {
        return this.map.containsKey(key);
    }

    /**
     * @param key
     * @return
     */
    @Override
    public T get(String key) {
        return this.map.get(key);
    }

    /**
     *
     */
    @Override
    public void clear() {
        this.map.clear();
    }

    /**
     *
     */
    @Override
    public List<T> elements() {
        return new ArrayList<>(this.map.values());
    }

    /**
     * @return
     */
    @Override
    public Map<String, T> asMap() {
        return new HashMap<>(this.map);
    }
}
