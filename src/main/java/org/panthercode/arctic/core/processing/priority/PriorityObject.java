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
package org.panthercode.arctic.core.processing.priority;

/**
 * This class is used to encapsulate an object with its priority.
 *
 * @author PantherCode
 * @see Priority
 * @see PriorityObject
 * @since 1.0
 */
public class PriorityObject<T> implements Comparable<PriorityObject<T>> {

    /**
     * actual priority of the object
     */
    private Priority priority = Priority.NORMAL;

    /**
     * actual body
     */
    private T content;

    /**
     * Standard Constructor
     */
    public PriorityObject() {
    }

    /**
     * Constructor
     *
     * @param content actual prioritised body
     */
    public PriorityObject(T content) {
        this(content, Priority.NORMAL);
    }

    /**
     * Constructor
     *
     * @param content  prioritised body
     * @param priority priority of the body
     */
    public PriorityObject(T content, Priority priority) {
        this.setPriority(priority);
        this.setContent(content);
    }

    /**
     * Sets the priority of the body. This function is <tt>synchronized</tt>.
     *
     * @param priority new priority of the body
     */
    public synchronized void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Returns the actual priority of the body.
     *
     * @return Returns the actual priority of the body.
     */
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * Sets the body of prioritised object. This function is <tt>synchronized</tt>.
     *
     * @param content new body of the object
     */
    public synchronized void setContent(T content) {
        this.content = content;
    }

    /**
     * Returns the actual body of prioritised object.
     *
     * @return Returns the actual body of prioritised object.
     */
    public T getContent() {
        return this.content;
    }

    /**
     * Compares this object to another <tt>PriorityObject</tt> instance.
     *
     * @param other other <tt>PriorityObject</tt> instance
     * @return TODO: documentation
     */
    @Override
    public int compareTo(PriorityObject<T> other) {
        return other.getPriority().toInteger() - this.getPriority().toInteger();
    }
}
