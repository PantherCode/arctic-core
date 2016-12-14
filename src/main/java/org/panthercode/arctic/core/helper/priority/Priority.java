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
 * TODO: documentation
 *
 * @author PantherCode
 */
public enum Priority {

    VERY_HIGH(1, "Very High"),

    HIGH(2, "High"),

    NORMAL(3, "Normal"),

    LOW(4, "Low"),

    VERY_LOW(5, "Very Low");

    private final int priority;

    private final String value;

    Priority(int priority, String value) {
        this.priority = priority;
        this.value = value;
    }

    public int priority() {
        return this.priority;
    }

    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value();
    }
}
