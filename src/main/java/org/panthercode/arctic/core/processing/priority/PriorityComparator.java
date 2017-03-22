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
 * Comparator class for priorities.
 *
 * @author PantherCode
 * @see Priority
 * @since  1.0
 */
public class PriorityComparator implements Comparator<Priority> {

    /**
     * Compares two <tt>Priority</tt> objects
     *
     * @param first first priority object
     * @param other second priority object
     * @return TODO: documentation
     */
    @Override
    public int compare(Priority first, Priority other) {
        return other.toInteger() - first.toInteger();
    }
}