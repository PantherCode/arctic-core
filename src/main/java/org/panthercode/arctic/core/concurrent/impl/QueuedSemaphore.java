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

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class queues threads and executed it by FIFO principle.
 *
 * @author PantherCode
 * @since 1.0
 */
public class QueuedSemaphore extends AbstractSemaphore<Void> {

    /**
     * collection with queued threads waiting in semaphore
     */
    private Queue<Thread> queue;

    /**
     * Standard Constructor
     */
    public QueuedSemaphore() {
        this(1);
    }

    /**
     * Constructor
     *
     * @param allowedParalleledThreads maximal count of allowed threads running parallel
     */
    public QueuedSemaphore(int allowedParalleledThreads) {
        super(allowedParalleledThreads);

        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * The actual thread enters the semaphore.
     *
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    @Override
    public synchronized void acquire() throws InterruptedException {
        this.acquire(null);
    }

    /**
     * The actual thread enters the semaphore.
     *
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    @Override
    public synchronized void acquire(Void value) throws InterruptedException {
        if (this.counter() == 0) {
            this.queue.add(Thread.currentThread());

            while (this.counter() == 0 || !Thread.currentThread().equals(this.queue.peek())) {
                this.wait();
            }

            this.queue.remove();
        }

        this.decrementCounter();
    }

    /**
     * Returns the number of queued threads.
     *
     * @return Returns the number of queued threads.
     */
    @Override
    public int getQueueLength() {
        return this.queue.size();
    }

    /**
     * Returns a flag that indicates whether the queue is empty or not.
     *
     * @return Return <tt>true</tt> if the queue contains elements; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean hasQueuedThreads() {
        return !this.queue.isEmpty();
    }
}
