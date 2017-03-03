package org.panthercode.arctic.core.collections.helper;

import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;

/**
 * TODO: implement AllocationException
 *
 * Created by architect on 27.02.17.
 */
public interface Allocator<V, K extends Versionable> {

    boolean contains(Object key);

    boolean contains(Object key, Version version);

    K allocate(Object key);

    K allocate(Object key, Version version);
}
