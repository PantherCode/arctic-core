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
package org.panthercode.arctic.core.concurrent.semaphore;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.semaphore.impl.*;
import org.panthercode.arctic.core.processing.priority.Priority;

/**
 * Utility class to create <tt>Semaphore</tt> classes.
 *
 * @author PantherCode
 * @see AbstractSemaphore
 * @see PriorityQueuedSemaphore
 * @see PrioritySemaphore
 * @see QueuedSemaphore
 * @see RandomSemaphore
 * @since 1.0
 */
public class Semaphores {

    /**
     * private Constructor
     */
    private Semaphores() {
    }

    /**
     * Returns a new instance of a <tt>PriorityQueuedSemaphore</tt> with one thread.
     *
     * @return Returns a new instance of a <tt>PriorityQueuedSemaphore</tt> with one thread.
     */
    public static Semaphore<Priority> createPriorityQueuedSemaphore() {
        return Semaphores.createPriorityQueuedSemaphore(1);
    }

    /**
     * Returns a new instance of a <tt>PriorityQueuedSemaphore</tt> with custom thread count.
     *
     * @param allowedParalleledThreads maximal count of parallel running threads
     * @return Returns a new instance of a <tt>PriorityQueuedSemaphore</tt> with custom thread count.
     */
    public static Semaphore<Priority> createPriorityQueuedSemaphore(int allowedParalleledThreads) {
        return new PriorityQueuedSemaphore(allowedParalleledThreads);
    }

    /**
     * Returns a new instance of a <tt>PrioritySemaphore</tt> with one thread.
     *
     * @return Returns a new instance of a <tt>PrioritySemaphore</tt> with one thread.
     */
    public static Semaphore<Priority> createPrioritySemaphore() {
        return Semaphores.createPrioritySemaphore(1);
    }

    /**
     * Returns a new instance of a <tt>PrioritySemaphore</tt> with custom thread count.
     *
     * @param allowedParalleledThreads maximal count of parallel running threads
     * @return Returns a new instance of a <tt>PrioritySemaphore</tt> with custom thread count.
     */
    public static Semaphore<Priority> createPrioritySemaphore(int allowedParalleledThreads) {
        return new PrioritySemaphore(allowedParalleledThreads);
    }

    /**
     * Returns a new instance of a <tt>PrioritySemaphore</tt> with one thread.
     *
     * @return Returns a new instance of a <tt>PrioritySemaphore</tt> with one thread.
     */
    public static Semaphore<Void> createQueuedSemaphore() {
        return Semaphores.createQueuedSemaphore(1);
    }

    /**
     * Returns a new instance of a <tt>QueuedSemaphore</tt> with custom thread count.
     *
     * @param allowedParalleledThreads maximal count of parallel running threads
     * @return Returns a new instance of a <tt>QueuedSemaphore</tt> with custom thread count.
     */
    public static Semaphore<Void> createQueuedSemaphore(int allowedParalleledThreads) {
        return new QueuedSemaphore(allowedParalleledThreads);
    }

    /**
     * Returns a new instance of a <tt>RandomSemaphore</tt> with one thread.
     *
     * @return Returns a new instance of a <tt>RandomSemaphore</tt> with one thread.
     */
    public static Semaphore<Void> createRandomSemaphore() {
        return Semaphores.createRandomSemaphore(1);
    }

    /**
     * Returns a new instance of a <tt>RandomSemaphore</tt> with custom thread count.
     *
     * @param allowedParalleledThreads maximal count of parallel running threads
     * @return Returns a new instance of a <tt>RandomSemaphore</tt> with custom thread count.
     */
    public static Semaphore<Void> createRandomSemaphore(int allowedParalleledThreads) {
        return new RandomSemaphore(allowedParalleledThreads);
    }

    /**
     * TODO: rename
     * Capsule a runnable in the context of a given semaphore.
     *
     * @param runnable       runnable for capsuling
     * @param semaphore      used semaphore
     * @param semaphoreValue value to calculate order in semaphore
     * @param <T>            generic class type
     * @return Returns a new <tt>Runnable</tt> object with capsulised original runnable.
     * @throws NullPointerException Is thrown if one parameter is <tt>null</tt>.
     */
    public static <T> Runnable addTo(Runnable runnable, Semaphore<T> semaphore, T semaphoreValue)
            throws NullPointerException {
        ArgumentUtils.checkNotNull(runnable, "runnable");
        ArgumentUtils.checkNotNull(semaphore, "semaphore");

        return new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire(semaphoreValue);

                    runnable.run();

                    semaphore.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
