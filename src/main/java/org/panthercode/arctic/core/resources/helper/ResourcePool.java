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

import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.repository.impl.RepositoryImpl;
import org.panthercode.arctic.core.resources.Resource;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

//TODO: update implementation
//TODO: documentation
/**
 *
 */
public class ResourcePool extends RepositoryImpl<ResourceHandler> {

    /**
     *
     * @param identity
     */
    public ResourcePool(Identity identity) {
        super(identity);
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public ResourceHandler get(String key) {
        throw new UnsupportedOperationException(); //TODO
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<Map<Version, ResourceHandler>> elements() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @return
     */
    @Override
    public Map<String, Map<Version, ResourceHandler>> asMap() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean canConnect(String name) {
        return false;
    }

    /**
     *
     * @param name
     * @return
     * @throws InterruptedException
     */
    public synchronized Resource connect(String name) throws InterruptedException {
        if (name != null) {
            if (this.contains(name)) {
                return this.get(name).create();
            }
        }

        return null;
    }

    /**
     *
     * @param resource
     * @throws IOException
     */
    public synchronized void disconnect(Resource resource) throws IOException {
        if(resource != null){
            if(this.contains(resource.identity().getName())){
                this.get(resource.identity().getName()).disconnect(resource);
            }
        }
    }
}