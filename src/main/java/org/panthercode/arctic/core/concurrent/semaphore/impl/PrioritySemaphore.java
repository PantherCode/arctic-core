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
package org.panthercode.arctic.core.concurrent.semaphore.impl;

import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.processing.priority.PriorityComparator;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * This class queues threads by its toInteger. Threads with higher toInteger will be executed privileged. Threads with
 * same toInteger will executed by random.
 *
 * @author PantherCode
 * @see Priority
 * @see PriorityQueuedSemaphore
 * @since 1.0
 */
public class PrioritySemaphore extends AbstractSemaphore<Priority> {

    /**
     * object to create random numbers
     */
    private Random random;

    /**
     * collection with queued threads waiting in semaphore
     */
    private List<Thread>[] queuedThreads;

    /**
     * collection with queued priorities corresponding to threads
     */
    private Queue<Priority> queuedPriorities;

    /**
     * Standard Constructor
     */
    public PrioritySemaphore() {
        this(1);
    }

    /**
     * Constructor
     *
     * @param allowedParalleledThreads maximal count of allowed threads running parallel
     */
    public PrioritySemaphore(int allowedParalleledThreads) {
        super(allowedParalleledThreads);

        this.random = new Random(System.currentTimeMillis());

        this.queuedPriorities = new PriorityBlockingQueue<>(10, new PriorityComparator());

        this.queuedThreads = new List[5];

        for (int i = 0; i < 5; i++) {
            this.queuedThreads[i] = Collections.synchronizedList(new ArrayList<>());
        }
    }

    /**
     * The actual thread enters the semaphore. The thread is added with <tt>Priority.NORMAL</tt>.
     *
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    @Override
    public synchronized void acquire()
            throws InterruptedException {
        this.acquire(Priority.NORMAL);
    }

    /**
     * The actual thread enters the semaphore. The thread is added with custom toInteger.
     *
     * @param value toInteger of thread
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    @Override
    public synchronized void acquire(Priority value)
            throws InterruptedException {
        if (this.counter() == 0) {
            int priority = value.toInteger() - 1;

            int index = this.random.nextInt(this.queuedThreads[priority].size() + 1);

            this.queuedThreads[priority].add(index, Thread.currentThread());

            this.queuedPriorities.add(value);

            while (this.counter() == 0 || !Thread.currentThread().equals(this.queuedThreads[this.queuedPriorities.peek().toInteger() - 1].get(0))) {
                this.wait();
            }

            this.queuedPriorities.remove();

            this.queuedThreads[priority].remove(Thread.currentThread());
        }

        this.decrementCounter();
    }

    /**
     * Returns the number of queued threads.
     *
     * @return Returns the number of queued threads.
     */
    @Override
    public int queueLength() {
        return this.queuedPriorities.size();
    }

    /**
     * Returns a flag that indicates whether the queue is empty or not.
     *
     * @return Return <tt>true</tt> if the queue contains elements; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean hasQueuedThreads() {
        return !this.queuedPriorities.isEmpty();
    }
}
