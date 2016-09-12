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
