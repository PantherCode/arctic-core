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
package org.panthercode.arctic.core.event;

/**
 * This interface is used to check or handle results of operations or processes asynchronously.
 * <p>
 * If you use <tt>Handler</tt> interface to control before and after state of an event, its
 * useful to capsule this information in an event class.
 *
 * @author PantherCode
 * @since 1.0
 */
public interface Handler<T> {

    /**
     * This function is called to commit changes to consumeHandler class.
     *
     * @param e event content
     */
    void handle(T e);
}
