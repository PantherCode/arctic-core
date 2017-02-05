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
package org.panthercode.arctic.core.processing;

/**
 * Enumeration with instructions to do next step in a defined way.
 *
 * @author PantherCode
 * @since 1.0
 */
public enum ProcessAction {
    /**
     * do nothing
     */
    IGNORE("Ignore"),

    /**
     * do something again
     */
    RETRY("Retry"),

    /**
     * abort the process
     */
    CANCEL("Cancel"),

    /**
     * begin a process
     */
    START("Start"),

    /**
     * close the application or process
     */
    SHUTDOWN("Shutdown"),

    /**
     * interrupt the execution for a short time
     */
    WAIT("Wait");

    /**
     * string value of field
     */
    final String value;

    /**
     * Constructor
     *
     * @param value value of state
     */
    ProcessAction(final String value) {
        this.value = value;
    }

    /**
     * Returns a string representing the process action.
     *
     * @return Returns a string representing the process action.
     */
    @Override
    public String toString() {
        return this.value;
    }
}
