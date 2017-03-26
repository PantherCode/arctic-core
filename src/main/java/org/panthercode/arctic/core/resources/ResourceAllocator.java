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

import org.panthercode.arctic.core.collections.VersionMap;
import org.panthercode.arctic.core.collections.helper.AllocationException;
import org.panthercode.arctic.core.collections.helper.DefaultAllocator;
import org.panthercode.arctic.core.helper.version.Version;

/**
 * @author PantherCode
 * @see VersionMap
 * @since 1.0
 */
public class ResourceAllocator extends DefaultAllocator<Object, Resource> {

    /**
     *
     * @param resourcePool
     */
    public ResourceAllocator(VersionMap<Object, Resource> resourcePool) {
        super(resourcePool);
    }

    /**
     *
     * @param key key of object
     * @return
     * @throws AllocationException
     */
    @Override
    public Resource allocate(Object key)
            throws AllocationException {
        Resource resource = super.allocate(key);

        return resource.copy();
    }

    /**
     *
     * @param key     key of object
     * @param version version of object
     * @return
     * @throws AllocationException
     */
    @Override
    public Resource allocate(Object key, Version version)
            throws AllocationException {
        Resource resource = super.allocate(key, version);

        return resource.copy();
    }
}
