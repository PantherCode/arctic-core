package org.panthercode.arctic.core.processing.modules;

import org.panthercode.arctic.core.collections.VersionMap;
import org.panthercode.arctic.core.collections.helper.VersionMapAllocator;
import org.panthercode.arctic.core.helper.version.Version;

/**
 * Created by architect on 18.01.17.
 */
public class ModuleAllocator extends VersionMapAllocator<Object, Module> {

    public ModuleAllocator(VersionMap<Object, Module> modules) {
        super(modules);
    }

    @Override
    public Module allocate(Object key)
            throws ModuleAllocationException {
        Module module = super.allocate(key);

        if (module == null) {
            throw new ModuleAllocationException("No module found.");
        }

        return module.copy();
    }

    @Override
    public Module allocate(Object key, Version version)
            throws ModuleAllocationException {
        Module module = super.allocate(key, version);

        if (module == null) {
            throw new ModuleAllocationException("No module found.");
        }

        return module.copy();
    }
}
