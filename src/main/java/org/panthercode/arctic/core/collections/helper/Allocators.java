package org.panthercode.arctic.core.collections.helper;

import org.panthercode.arctic.core.collections.VersionMap;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.processing.modules.ModuleAllocator;
import org.panthercode.arctic.core.resources.Resource;
import org.panthercode.arctic.core.resources.ResourceAllocator;

/**
 * Created by architect on 28.02.17.
 */
public class Allocators {

    private Allocators() {
    }

    public static <K, V extends Versionable> Allocator<K, V> createDefaultAllocator(VersionMap<K, V> map) {
        return new VersionMapAllocator<>(map);
    }

    public static Allocator<Object, Module> createModuleAllocator(VersionMap<Object, Module> map) {
        return new ModuleAllocator(map);
    }

    public static Allocator<Object, Resource> createResourceAllocator(VersionMap<Object, Resource> map) {
        return new ResourceAllocator(map);
    }
}
