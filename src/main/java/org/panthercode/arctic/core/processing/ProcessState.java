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
 * Enumeration of possible processing states. It's used indicate the inner state of an object.
 */
public enum ProcessState {
    /**
     * State after the object is created.
     */
    NEW("New"),

    /**
     * The process was aborted. A smoother form of stopped.
     */
    CANCELLED("Cancelled"),

    /**
     * The run finished unsuccessfully.
     */
    FAILED("Failed"),

    /**
     * The object is preparing.
     */
    INITIALIZING("Initializing"),

    /**
     * The object is ready to execute.
     */
    READY("Ready"),

    /**
     * The object is executing its functionality.
     */
    RUNNING("Running"),

    /**
     * The object is sorted.
     */
    SCHEDULING("Scheduling"),

    /**
     * The execution was aborted.
     */
    STOPPED("Stopped"),

    /**
     * The run finished successful.
     */
    SUCCEEDED("Succeeded"),

    /**
     * The object's execution is interrupted for short time by some reason.
     */
    WAITING("Waiting");

    /**
     * string value of state
     */
    private final String value;

    /**
     * Constructor
     *
     * @param value value of state
     */
    ProcessState(String value) {
        this.value = value;
    }

    /**
     * Returns a string representing the process state.
     *
     * @return Returns a string representing the process state.
     */
    public String value() {
        return this.value;
    }

    /**
     * Returns a string representing the process state.
     *
     * @return Returns a string representing the process state.
     */
    @Override
    public String toString() {
        return this.value;
    }
}
