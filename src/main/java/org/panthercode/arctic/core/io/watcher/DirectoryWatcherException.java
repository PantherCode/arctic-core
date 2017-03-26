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
package org.panthercode.arctic.core.io.watcher;

/**
 * This class is used if an error is occurred while the watcher is running.
 * TODO: documentation
 *
 * @author PantherCode
 * @since 1.0
 */
public class DirectoryWatcherException extends RuntimeException {

    /**
     *
     */
    public DirectoryWatcherException() {
        super();
    }

    /**
     *
     * @param message
     */
    public DirectoryWatcherException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public DirectoryWatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public DirectoryWatcherException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected DirectoryWatcherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
