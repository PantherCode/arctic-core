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
package org.panthercode.arctic.core.processing.priority;

import java.util.Comparator;

/**
 * Comparator for <tt>PriorityObject</tt> class
 *
 * @author PantherCode
 * @see Priority
 * @see PriorityObject
 * @since 1.0
 */
public class PriorityObjectComparator<T extends PriorityObject> implements Comparator<T> {

    /**
     * Compares two <tt>PriorityObject</tt> instances
     *
     * @param first first <tt>PriorityObject</tt>
     * @param other second <tt>PriorityObject</tt>
     * @return TODO: documentation
     */
    @Override
    public int compare(T first, T other) {
        return other.getPriority().toInteger() - first.getPriority().toInteger();
    }
}
