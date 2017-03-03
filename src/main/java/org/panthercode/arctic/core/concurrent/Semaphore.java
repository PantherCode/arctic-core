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
package org.panthercode.arctic.core.concurrent;

/**
 * Interface to implement a semaphore class.
 * <p>
 * To implement a critical section, every thread need to call semaphore's <tt>acquire()</tt> method:
 * <pre>
 * //Creates a new QueuedSemaphore to handle critical section
 * Semaphore<Void> semaphore =  Semaphores.createQueuedSemaphore();
 * ...
 * semaphore.acquire();
 * //critical section
 * semaphore.release();
 * </pre>
 * Attention: Be sure semaphores are always initialized in a not thread-private context, but can be seen by every thread
 * which shall controlled before enter a critical section.
 * <p>
 * Semaphores are used to handle critical sections created by a limited amount of resources. If instead every thread is
 * independent, but you want to limit the number of paralleled running threads, than use the <tt>WorkerOld</tt> class in
 * combination with <tt>Tasks</tt>.
 *
 * @author PantherCode
 * @see WorkerOld
 * @see Task
 * @since 1.0
 */
public interface Semaphore<T> {

    /**
     * The actual thread enters the semaphore.
     *
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    void acquire() throws Exception;

    /**
     * The actual thread enters the semaphore. The thread is added with custom toInteger.
     *
     * @param value toInteger of thread
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    void acquire(T value) throws Exception;

    /**
     * The current thread will exit semaphore and increment counter by one.
     */
    void release();

    /**
     * Returns the actual count of running threads.
     *
     * @return Returns the actual count of running threads.
     */
    int actualThreadCount();

    /**
     * Returns the maximal count of allowed running threads.
     *
     * @return Returns the maximal count of allowed running threads.
     */
    int allowedParalleledThreads();

    /**
     * Returns the number of queued threads.
     *
     * @return Returns the number of queued threads.
     */
    int queueLength();

    /**
     * Returns a flag that indicates whether the queue is empty or not.
     *
     * @return Return <tt>true</tt> if the queue contains elements; Otherwise <tt>false</tt>.
     */
    boolean hasQueuedThreads();
}
