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

import org.panthercode.arctic.core.concurrent.semaphore.Semaphore;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.settings.Configuration;

/**
 * Abstract class to
 *
 * @author PantherCode
 * @since 1.0
 */
@IdentityInfo(name = "Abstract Critical Resource", group = "Resources")
@VersionInfo(major = 1, minor = 0, build = 0, revision = 0)
public abstract class AbstractCriticalResource extends AbstractResource {

    /**
     *
     */
    private Semaphore<Priority> semaphore = null;

    /**
     *
     * @param semaphore
     */
    public AbstractCriticalResource(Semaphore<Priority> semaphore) {
        this(semaphore, null);
    }

    /**
     *
     * @param semaphore
     * @param configuration
     */
    public AbstractCriticalResource(Semaphore<Priority> semaphore,
                                    Configuration configuration) {
        super(configuration);

        this.semaphore = semaphore;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isBusy() {
        return this.semaphore.hasQueuedThreads();
    }

    /**
     *
     * @return
     */
    @Override
    public int actualThreadCount() {
        return this.semaphore.actualThreadCount();
    }

    /**
     *
     * @return
     */
    @Override
    public int allowedParalleledThreads() {
        return this.semaphore.allowedParalleledThreads();
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void acquire() throws Exception {
        this.acquire(Priority.NORMAL);
    }

    /**
     *
     * @param priority
     * @throws Exception
     */
    @Override
    public void acquire(Priority priority) throws Exception {
        this.semaphore.acquire(priority);

        super.acquire(priority);
    }

    /**
     *
     */
    @Override
    public void release() {
        this.semaphore.release();

        super.release();
    }
}
