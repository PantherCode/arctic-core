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

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.reflect.ReflectionUtils;
import org.panthercode.arctic.core.settings.Configurable;
import org.panthercode.arctic.core.settings.Configuration;

/**
 * TODO: documentation
 *
 * @author PantherCode
 * @since 1.0
 */
@IdentityInfo(name = "Abstract Resource", group = "Resources")
@VersionInfo(major = 1, minor = 0, build = 0, revision = 0)
public abstract class AbstractResource implements Resource, Identifiable, Versionable, Configurable {

    /**
     *
     */
    private final Version version;

    /**
     *
     */
    private final Identity identity;

    /**
     *
     */
    private boolean isOpen = false;

    /**
     *
     */
    protected Configuration configuration = null;

    /**
     *
     */
    public AbstractResource() {
        this(null);
    }

    /**
     * @param configuration
     */
    public AbstractResource(Configuration configuration) {
        this.version = Version.fromAnnotation(this.getClass());

        this.identity = Identity.fromAnnotation(this.getClass());

        this.configure(configuration);
    }

    /**
     * @return
     */
    @Override
    public Version version() {
        return this.version.copy();
    }

    /**
     * @return
     */
    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    /**
     * @param configuration new configuration associated with the object
     */
    @Override
    public synchronized void configure(Configuration configuration) {
        this.configuration = (configuration == null) ? new Configuration() : configuration;
    }

    /**
     * @return
     */
    @Override
    public Configuration configuration() {
        return this.configuration;
    }

    /**
     * @return
     */
    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    /**
     * @return
     */
    @Override
    public boolean isBusy() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public int actualThreadCount() {
        return 1;
    }

    /**
     * @return
     */
    @Override
    public int allowedParalleledThreads() {
        return Integer.MAX_VALUE;
    }

    /**
     * @throws Exception
     */
    @Override
    public void acquire() throws Exception {
        this.acquire(Priority.NORMAL);
    }

    /**
     * @param priority
     * @throws Exception
     */
    @Override
    public void acquire(Priority priority) throws Exception {
        this.isOpen = true;
    }

    /**
     *
     */
    @Override
    public void release() {
        this.isOpen = false;
    }

    /**
     * @param functionName
     * @param returnType
     * @param arguments
     * @param <T>
     * @return
     * @throws Exception
     */
    public synchronized <T> T execute(String functionName, Class<T> returnType, Object... arguments) throws Exception {
        ArgumentUtils.checkNotNull(functionName, "function name");
        ArgumentUtils.checkNotNull(returnType, "return type");

        if (this.isOpen) {
            return ReflectionUtils.invokeMethod(this, functionName, returnType, arguments);
        }

        throw new Exception("The resource is closed.");
    }
}
