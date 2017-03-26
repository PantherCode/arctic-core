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
 * Exception class to communicate a disruption in processing.
 *
 * @author PantherCode
 * @since 1.0
 */
public class ProcessException extends RuntimeException {
    /**
     *
     */
    public ProcessException() {
        super();
    }

    /**
     *
     * @param message
     */
    public ProcessException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public ProcessException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
