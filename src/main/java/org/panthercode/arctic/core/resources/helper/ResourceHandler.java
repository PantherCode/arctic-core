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
package org.panthercode.arctic.core.resources.helper;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.resources.impl.ResourceImpl;
import org.panthercode.arctic.core.resources.Resource;
import org.panthercode.arctic.core.settings.Configuration;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO: update implementation
//TODO: documentation
/**
 *
 */
public class ResourceHandler implements Identifiable {

    /**
     *
     */
    private int currentConnections = 0;

    /**
     *
     */
    private int maximalConnectinos = 1;

    /**
     *
     */
    private Identity identity = null;

    /**
     *
     */
    private Configuration initialConfiguration = null;

    /**
     *
     */
    List<Resource> resourceInstances = new ArrayList<>(); //BlockingQueue verwenden

    /**
     *
     */
    private final ResourceImpl resource;

    /**
     *
     */
    private final Object LOCK = new Object();

    /**
     * @param identity
     * @param resource
     * @param maximalConnectinos
     */
    public ResourceHandler(Identity identity, ResourceImpl resource, int maximalConnectinos) {
        this(identity, resource, maximalConnectinos, null);
    }

    /**
     * @param identity
     * @param resource
     * @param maximalConnectinos
     * @param defaultConfig
     */
    //TODO: parameter setzen
    public ResourceHandler(Identity identity, ResourceImpl resource, int maximalConnectinos, Configuration defaultConfig) {
        ArgumentUtils.assertNotNull(identity, "identity");
        ArgumentUtils.assertNotNull(resource, "resource");

        this.resource = resource;
    }

    /**
     * @return
     */
    public Identity identity() {
        return this.identity.copy();
    }

    /**
     * @return
     */
    public int currentConnections() {
        return this.currentConnections;
    }

    /**
     * @return
     */
    public int maximumConnections() {
        return this.maximalConnectinos;
    }

    /**
     * @return
     */
    public Configuration getInitialConfiguration() {
        return this.initialConfiguration;
    }

    /**
     * @param configuration
     */
    public void setInitialConfiguration(Configuration configuration) {
        this.initialConfiguration = (configuration == null) ? new Configuration() : configuration;
    }

    /**
     * @return
     * @throws InterruptedException
     */
    public Resource create() throws InterruptedException {
        synchronized (LOCK) {
            currentConnections++;

            while (currentConnections > maximalConnectinos) {
                LOCK.wait();
            }
        }

        Resource newResource = this.resource.clone();

        this.resourceInstances.add(newResource);

        return newResource;
    }

    /**
     * @param resource
     * @throws IOException
     */
    public synchronized void disconnect(Resource resource) throws IOException {
        if (resource != null) {
            if (this.resourceInstances.contains(resource)) {
                currentConnections--;

                this.resourceInstances.remove(resource);

                if (resource instanceof Closeable) {
                    if (!resource.isClosed()) {
                        ((Closeable) resource).close();
                    }
                }
            } else {
                throw new RuntimeException(); //TODO: Wrong ResourceHandler for Resource
            }
        }
    }
}
