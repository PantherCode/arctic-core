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

import org.panthercode.arctic.core.helper.identity.Identifiable;

import java.util.Collection;
import java.util.Map;

/**
 * Repositories are used to store classes identified by its identify name.
 */
public interface Repository<T extends Identifiable> extends Identifiable {

    /**
     * Adds a new element to repository.
     *
     * @param element element to add
     */
    void store(T element);

    /**
     * Removes an element from repository.
     *
     * @param key element to remove
     */
    void delete(String key);

    /**
     * Checks whether the repository contains an element with given name or not.
     *
     * @param key name of module
     * @return Returns <tt>true</tt> if repository contains the element; Otherwise <tt>false</tt>.
     */
    boolean contains(String key);

    /**
     * Returns an element from repository.
     *
     * @param key name of element
     * @return Returns the element if repository contains the object; Otherwise <tt>null</tt>.
     */
    T get(String key);

    /**
     * Removes all elements from repository.
     */
    void clear();

    /**
     * Returns a collection with all elements stored in this object.
     *
     * @return Returns a collection with all elements stored in this object.
     */
    Collection<T> elements();

    /**
     * Returns a map with all elements stored in this object.
     *
     * @return Returns a collection with all elements stored in this object.
     */
    Map<String, T> asMap();
}
