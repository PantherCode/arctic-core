package org.panthercode.arctic.core.collections.helper;

import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.Versionable;

/**
 *
 * <p>
 * Created by architect on 27.02.17.
 */
public interface Allocator<K, V extends Versionable> {
    /**
     *
     * @param key
     * @return
     */
    boolean contains(Object key);

    /**
     *
     * @param key
     * @param version
     * @return
     */
    boolean contains(Object key, Version version);

    /**
     *
     * @param key
     * @return
     * @throws AllocationException
     */
    V allocate(Object key) throws AllocationException;

    /**
     *
     * @param key
     * @param version
     * @return
     * @throws AllocationException
     */
    V allocate(Object key, Version version) throws AllocationException;
}
