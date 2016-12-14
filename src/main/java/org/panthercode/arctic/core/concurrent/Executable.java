package org.panthercode.arctic.core.concurrent;

import org.panthercode.arctic.core.factory.Options;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public interface Executable<T> {

    T execute(Options options);
}
