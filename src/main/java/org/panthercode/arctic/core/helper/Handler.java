package org.panthercode.arctic.core.helper;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public interface Handler<E> {

    void handle(E event);
}
