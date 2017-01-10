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
package org.panthercode.arctic.core.concurrent.impl;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.Semaphore;

/**
 * The <tt>AbstractSemaphore</tt> class contains the basic functionality to implement a semaphore.
 *
 * @author PantherCode
 * @see PriorityQueuedSemaphore
 * @see PrioritySemaphore
 * @see QueuedSemaphore
 * @see RandomSemaphore
 * @since 1.0
 */
public abstract class AbstractSemaphore<T> implements Semaphore<T> {

    /**
     * internal counter to control the access to critical section
     */
    private int counter = 0;

    /**
     * maximal count of allowed threads
     */
    private final int allowedParalleledThreads;

    /**
     * Standard Constructor
     */
    public AbstractSemaphore() {
        this(1);
    }

    /**
     * Constructor
     *
     * @param allowedParalleledThreads maximal count of allowed threads running parallel
     */
    public AbstractSemaphore(int allowedParalleledThreads) {
        ArgumentUtils.assertGreaterZero(allowedParalleledThreads, "allowed paralleled threads");

        this.allowedParalleledThreads = this.counter = allowedParalleledThreads;
    }

    /**
     * The current thread will exit semaphore and increment counter by one.
     */
    @Override
    public synchronized void release() {
        if (this.counter() < this.getAllowedParalleledThreads()) {
            this.notifyAll();
        }

        this.incrementCounter();
    }

    /**
     * Returns the actual count of running threads.
     *
     * @return Returns the actual count of running threads.
     */
    @Override
    public int getActualThreadCount() {
        return this.allowedParalleledThreads - this.counter;
    }

    /**
     * Returns the maximal count of allowed running threads.
     *
     * @return Returns the maximal count of allowed running threads.
     */
    @Override
    public int getAllowedParalleledThreads() {
        return this.allowedParalleledThreads;
    }

    /**
     * Increment the counter by one.
     */
    protected synchronized void incrementCounter() {
        this.counter++;
    }

    /**
     * Decrement the counter by one.
     */
    protected synchronized void decrementCounter() {
        this.counter--;
    }

    /**
     * Returns the actual value of the counter.
     *
     * @return Returns the actual value of the counter.
     */
    protected int counter() {
        return this.counter;
    }
}
