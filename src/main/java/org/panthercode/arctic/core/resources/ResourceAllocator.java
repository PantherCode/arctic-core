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
import org.panthercode.arctic.core.collections.helper.VersionMapAllocator;
import org.panthercode.arctic.core.helper.version.Version;

/**
 * @author PantherCode
 */
public class ResourceAllocator extends VersionMapAllocator<Object, Resource> {

    public ResourceAllocator(VersionMap<Object, Resource> resourcePool) {
        super(resourcePool);
    }

    @Override
    public Resource allocate(Object key) {
        Resource resource = super.allocate(key);

        if (resource == null) {
            throw new ResourceAllocationExcepion("No resource found.");
        }

        return resource.copy();
    }

    @Override
    public Resource allocate(Object key, Version version) {
        Resource resource = super.allocate(key, version);

        if (resource == null) {
            throw new ResourceAllocationExcepion("No resource found");
        }

        return resource.copy();
    }
}
