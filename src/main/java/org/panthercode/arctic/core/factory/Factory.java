package org.panthercode.arctic.core.factory;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public interface Factory<V, K> {
    V create(K key);

    V create(K key, Options options);
}
