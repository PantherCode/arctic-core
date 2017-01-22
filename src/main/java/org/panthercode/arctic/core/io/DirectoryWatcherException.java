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
package org.panthercode.arctic.core.io;

/**
 * @author PantherCode
 * @since 1.0
 */
public class DirectoryWatcherException extends RuntimeException {

    /**
     * Default Constructor
     */
    public DirectoryWatcherException() {
        super();
    }

    /**
     * Constructor
     *
     * @param message message of exception
     */
    public DirectoryWatcherException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message message of exception
     * @param cause   cause of exception
     */
    public DirectoryWatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     *
     * @param cause cause of exception
     */
    public DirectoryWatcherException(Throwable cause) {
        super(cause);
    }
}