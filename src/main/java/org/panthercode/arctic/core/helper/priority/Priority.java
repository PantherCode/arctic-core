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
package org.panthercode.arctic.core.helper.priority;

/**
 * Enumeration to represent a set of priorities.
 *
 * @author PantherCode
 * @since 1.0
 */
public enum Priority {

    /**
     * Very High
     */
    VERY_HIGH(5, "Very High"),

    /**
     * High
     */
    HIGH(4, "High"),

    /**
     * Normal
     */
    NORMAL(3, "Normal"),

    /**
     * Low
     */
    LOW(2, "Low"),

    /**
     * Very Low
     */
    VERY_LOW(1, "Very Low");

    /**
     * actual value of toInteger as integer
     */
    private final int priorityAsInteger;

    /**
     * actual value of toInteger as string
     */
    private final String priorityAsString;

    /**
     * Constructor
     *
     * @param priorityAsInteger value of priority as integer
     * @param priorityAsString  value of priority as string
     */
    Priority(int priorityAsInteger, String priorityAsString) {
        this.priorityAsInteger = priorityAsInteger;
        this.priorityAsString = priorityAsString;
    }

    /**
     * Returns an integer representation of the object.
     *
     * @return Returns an integer representation of the object.
     */
    public int toInteger() {
        return this.priorityAsInteger;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return this.priorityAsString;
    }
}
