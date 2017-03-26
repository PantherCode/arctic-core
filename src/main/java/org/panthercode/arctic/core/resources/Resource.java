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
package org.panthercode.arctic.core.resources;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.settings.Configurable;

/**
 * TODO: documentation
 *
 * @author PantherCode
 * @since 1.0
 */
public interface Resource extends Identifiable, Versionable, Configurable {
    /**
     *
     * @return
     */
    boolean isOpen();

    /**
     *
     * @return
     */
    boolean isBusy();

    /**
     *
     * @return
     */
    int actualThreadCount();

    /**
     *
     * @return
     */
    int allowedParalleledThreads();

    /**
     *
     * @throws Exception
     */
    void acquire() throws Exception;

    /**
     *
     * @param priority
     * @throws Exception
     */
    void acquire(Priority priority) throws Exception;

    /**
     *
     */
    void release();

    /**
     *
     * @return
     */
    Resource copy();

    /**
     *
     * @param functionName
     * @param returnType
     * @param arguments
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T execute(String functionName, Class<T> returnType, Object... arguments) throws Exception;
}
