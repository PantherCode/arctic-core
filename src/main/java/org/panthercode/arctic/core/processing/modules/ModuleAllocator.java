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
package org.panthercode.arctic.core.processing.modules;

import org.panthercode.arctic.core.collections.VersionMap;
import org.panthercode.arctic.core.collections.helper.AllocationException;
import org.panthercode.arctic.core.collections.helper.DefaultAllocator;
import org.panthercode.arctic.core.helper.version.Version;

/**
 * Class to control access to a module repository.
 *
 * @author PantherCode
 * @see VersionMap
 * @since 1.0
 */
public class ModuleAllocator extends DefaultAllocator<Object, Module> {

    /**
     * Constructor
     *
     * @param modules <tt>VersionMap</tt> object with modules
     */
    public ModuleAllocator(VersionMap<Object, Module> modules) {
        super(modules);
    }

    /**
     * Returns the module which is represented by given key.
     *
     * @param key key of module
     * @return Returns the object if the map contains it
     * @throws AllocationException Is thrown if the module repository doesn't contain the given key.
     */
    @Override
    public Module allocate(Object key)
            throws AllocationException {
        Module module = super.allocate(key);

        return module.copy();
    }

    /**
     * Returns the module with specific version which is represented by given key.
     *
     * @param key     key of module
     * @param version version of module
     * @return Returns the module if the map contains it.
     * @throws AllocationException Is thrown if the module repository doesn't contain the given element.
     */
    @Override
    public Module allocate(Object key, Version version)
            throws AllocationException {
        Module module = super.allocate(key, version);

        return module.copy();
    }
}
