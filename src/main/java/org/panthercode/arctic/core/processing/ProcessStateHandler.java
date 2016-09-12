package org.panthercode.arctic.core.processing;

import org.panthercode.arctic.core.processing.module.Module;

/**
 * The ProcessStateHandler is a simple kind of event handling. Whenever an object changes his process state an "event"
 * is raised, the handle() method will be called.
 */
public interface ProcessStateHandler {

    /**
     * Function used to react to <tt>ProcessState</tt> changes. You should call this function after the new value is
     * set to object. Therefore it's possible to see old and new value.
     *
     * @param module   module, that raised the event
     * @param oldState state of object before change
     * @throws Exception Is eventually thrown by the concrete implementation of this interface.
     */
    void handle(Module module, ProcessState oldState) throws Exception;
}
