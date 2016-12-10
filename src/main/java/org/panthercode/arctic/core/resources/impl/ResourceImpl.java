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
package org.panthercode.arctic.core.resources.impl;

/**
 *
 */

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.priority.Semaphore;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.reflect.ReflectionUtils;
import org.panthercode.arctic.core.resources.Resource;
import org.panthercode.arctic.core.resources.AbstractResource;
import org.panthercode.arctic.core.settings.Configuration;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class ResourceImpl implements Resource {

    private boolean isOpen = false;

    private AbstractResource resource;

    private Semaphore semaphore;

    private Priority priority;

    public ResourceImpl(AbstractResource resource,
                        Semaphore semaphore,
                        Priority priority) {
        this(resource, semaphore, priority, null);
    }

    public ResourceImpl(AbstractResource resource,
                        Semaphore semaphore,
                        Priority priority,
                        Configuration configuration) {
        ArgumentUtils.assertNotNull(resource, "resource");
        ArgumentUtils.assertNotNull(semaphore, "semaphore");
        ArgumentUtils.assertNotNull(priority, "priority");

        this.configure(configuration);

        this.resource = resource;

        this.semaphore = semaphore;

        this.priority = priority;
    }

    @Override
    public Version version() {
        return resource.version().copy();
    }

    @Override
    public Identity identity() {
        return resource.identity().copy();
    }

    @Override
    public synchronized void configure(Configuration configuration) {
        this.resource.configure(configuration);
    }

    @Override
    public Configuration configuration() {
        return this.configuration();
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public boolean isBusy() {
        return this.counter() == 0;
    }

    @Override
    public int counter() {
        return this.semaphore.counter();
    }

    @Override
    public int capacity() {
        return this.semaphore.capacity();
    }

    @Override
    public synchronized void acquire() throws Exception {
        if (!this.isOpen) {
            this.semaphore.acquire(this.priority);

            this.isOpen = true;
        }
    }

    @Override
    public synchronized void release() {
        if (this.isOpen) {
            this.semaphore.release();
        }
    }

    @Override
    public synchronized <T> T execute(String functionName, Class<T> returnType, Object... arguments) throws Exception {
        ArgumentUtils.assertNotNull(functionName, "c");

        if (this.isOpen) {
            if (returnType == null) {
                ReflectionUtils.invokeMethod(this.resource, functionName, arguments);

                return null;
            } else {
                return ReflectionUtils.invokeMethod(this.resource, functionName, returnType, arguments);
            }
        }

        throw new Exception("The resource is closed.");
    }
}
