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
 * Created by architect on 21.09.16.
 */
public interface Repository<T extends Identifiable> extends Identifiable {

    /**
     * @param element
     */
    void store(T element);

    /**
     * @param key
     */
    void delete(String key);

    /**
     * @param key
     * @return
     */
    boolean contains(String key);

    /**
     * @param key
     * @return
     */
    T get(String key);

    /**
     *
     */
    void clear();

    /**
     * @return
     */
    Collection<T> elements();

    /**
     * @return
     */
    Map<String, T> asMap();
}
