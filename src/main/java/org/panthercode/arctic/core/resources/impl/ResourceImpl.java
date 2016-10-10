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

import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.resources.Resource;
import org.panthercode.arctic.core.settings.configuration.ConfigurableObject;
import org.panthercode.arctic.core.settings.configuration.Configuration;

import java.io.Closeable;

/**
 *
 */
public abstract class ResourceImpl extends ConfigurableObject implements Resource, Cloneable, Closeable {

    /**
     *
     */
    private final Identity identity;

    /**
     *
     */
    private final Version version;

    /**
     *
     */
    protected boolean isCloned = false;

    /**
     *
     */
    private boolean isClosed;


    public ResourceImpl() {
        this(null);
    }

    public ResourceImpl(Configuration configuration) {
        this(null, configuration);
    }

    /**
     * @param identity
     * @param configuration
     */
    public ResourceImpl(Identity identity, Configuration configuration) {
        if (identity != null) {
            this.identity = identity;
        } else {
            if (Identity.isAnnotated(this)) {
                this.identity = Identity.fromAnnotation(this);
            } else {
                throw new NullPointerException("The value of identity is null.");
            }
        }

        if (Version.isAnnotated(this)) {
            this.version = Version.fromAnnotation(this);
        } else {
            this.version = new Version();
        }

        this.configure(configuration);
    }

    /**
     * @return
     */
    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    @Override
    public Version version() {
        return new Version(this.version);
    }

    /**
     * @return
     */
    public boolean isCloned() {
        return this.isCloned;
    }

    /**
     * @return
     */
    public boolean isClosed() {
        return this.isClosed;
    }

    /**
     *
     */
    public void close() {
        this.isClosed = true;
    }

    /**
     * @return
     */
    public abstract ResourceImpl clone();

    /**
     * Should to be synchronized
     *
     * @param command
     * @param returnType
     * @param arguments
     * @param <T>
     * @return
     */
    public abstract <T> T execute(String command, Class<T> returnType, Object... arguments) throws Exception;
}
