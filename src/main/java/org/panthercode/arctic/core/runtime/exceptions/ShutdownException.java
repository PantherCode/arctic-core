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
package org.panthercode.arctic.core.runtime.exceptions;

/**
 * Class to close the application in a controlled way.
 *
 * @author PantherCode
 */
public class ShutdownException extends RuntimeException {

    /**
     * flag to signalise whether the exception is handled or not
     */
    private boolean isHandled = false;

    /**
     * Default Constructor
     */
    public ShutdownException() {
        super();
    }

    /**
     * Constructor
     *
     * @param message message of exception
     */
    public ShutdownException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message message of exception
     * @param cause   cause of exception
     */
    public ShutdownException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     *
     * @param cause cause of exception
     */
    public ShutdownException(Throwable cause) {
        super(cause);
    }

    /**
     * Returns a flag to signalise whether the exception is handled or not.
     *
     * @return Returns <tt>true</tt> if flag is set; Otherwise <tt>false</tt>.
     */
    public boolean isHandled() {
        return this.isHandled;
    }

    /**
     * Sets flag whether the exception is handled or not.
     *
     * @param isHandled flag whether exceptions is handled or not
     */
    public void handled(boolean isHandled) {
        this.isHandled = isHandled;
    }
}
