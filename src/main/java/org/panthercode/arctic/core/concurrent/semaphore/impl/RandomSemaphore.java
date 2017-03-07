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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class queues threads and executed it by random.
 *
 * @author PantherCode
 * @since 1.0
 */
public class RandomSemaphore extends AbstractSemaphore<Void> {

    /**
     * object to create random numbers
     */
    private Random random;

    /**
     * collection with queued threads waiting in semaphore
     */
    private List<Thread> queuedThreads;

    /**
     * Standard Constructor
     */
    public RandomSemaphore() {
        this(1);
    }

    /**
     * Constructor
     *
     * @param allowedParalleledThreads maximal count of allowed threads running parallel
     */
    public RandomSemaphore(int allowedParalleledThreads) {
        super(allowedParalleledThreads);

        this.random = new Random(System.currentTimeMillis());

        this.queuedThreads = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * The actual thread enters the semaphore.
     *
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    @Override
    public synchronized void acquire() throws Exception {
        this.acquire(null);
    }

    /**
     * The actual thread enters the semaphore.
     *
     * @throws InterruptedException Is thrown if thread is interrupted.
     */
    @Override
    public synchronized void acquire(Void value) throws Exception {
        if (this.counter() == 0) {
            int index = this.random.nextInt(this.queuedThreads.size() + 1);

            this.queuedThreads.add(index, Thread.currentThread());

            while (this.counter() == 0 || !Thread.currentThread().equals(this.queuedThreads.get(0))) {
                this.wait();
            }

            this.queuedThreads.remove(Thread.currentThread());
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
        return this.queuedThreads.size();
    }

    /**
     * Returns a flag that indicates whether the queue is empty or not.
     *
     * @return Return <tt>true</tt> if the queue contains elements; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean hasQueuedThreads() {
        return !this.queuedThreads.isEmpty();
    }
}
