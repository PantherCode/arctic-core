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
import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.reflect.ReflectionUtils;
import org.panthercode.arctic.core.settings.Configurable;
import org.panthercode.arctic.core.settings.Configuration;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
@IdentityInfo(name = "Abstract Resource", group = "Resources")
@VersionInfo(major = 1, minor = 0, build = 0, revision = 0)
public abstract class AbstractResource implements Resource, Identifiable, Versionable, Configurable {

    private final Version version;

    private final Identity identity;

    private boolean isOpen = false;

    protected Configuration configuration = null;

    public AbstractResource() {
        this(null);
    }

    public AbstractResource(Configuration configuration) {
        this.version = Version.fromAnnotation(this.getClass());

        this.identity = Identity.fromAnnotation(this.getClass());

        this.configure(configuration);
    }

    @Override
    public Version version() {
        return this.version.copy();
    }

    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    @Override
    public synchronized void configure(Configuration configuration) {
        this.configuration = (configuration == null) ? new Configuration() : configuration;
    }

    @Override
    public Configuration configuration() {
        return this.configuration;
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public int counter() {
        return 1;
    }

    @Override
    public int capacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void acquire() throws Exception {
        this.acquire(Priority.NORMAL);
    }

    @Override
    public void acquire(Priority priority) throws Exception {
        this.isOpen = true;
    }

    @Override
    public void release() {
        this.isOpen = false;
    }

    public synchronized <T> T execute(String functionName, Class<T> returnType, Object... arguments) throws Exception {
        ArgumentUtils.assertNotNull(functionName, "function name");
        ArgumentUtils.assertNotNull(returnType, "return type");

        if (this.isOpen) {
            return ReflectionUtils.invokeMethod(this, functionName, returnType, arguments);
        }

        throw new Exception("The resource is closed.");
    }
}
