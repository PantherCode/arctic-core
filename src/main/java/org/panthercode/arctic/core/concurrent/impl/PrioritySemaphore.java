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

import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.priority.PriorityComparator;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class PrioritySemaphore extends AbstractSemaphore<Priority> {

    private Random random;

    private List<Thread>[] queuedThreads;

    private Queue<Priority> queuedPriorities;

    public PrioritySemaphore() {
        this(1);
    }

    public PrioritySemaphore(int capacity) {
        super(capacity);

        this.random = new Random(System.currentTimeMillis());

        this.queuedPriorities = new PriorityBlockingQueue<>(10, new PriorityComparator());

        this.queuedThreads = new List[5];

        for (int i = 0; i < 5; i++) {
            this.queuedThreads[i] = Collections.synchronizedList(new ArrayList<>());
        }
    }

    @Override
    public synchronized void acquire()
            throws InterruptedException {
        this.acquire(Priority.NORMAL);
    }

    @Override
    public synchronized void acquire(Priority value)
            throws InterruptedException {
        if (this.counter() == 0) {
            int priority = value.priority() - 1;

            int index = this.random.nextInt(this.queuedThreads[priority].size() + 1);

            this.queuedThreads[priority].add(index, Thread.currentThread());

            this.queuedPriorities.add(value);

            while (this.counter() == 0 || !Thread.currentThread().equals(this.queuedThreads[this.queuedPriorities.peek().priority() - 1].get(0))) {
                this.wait();
            }

            this.queuedPriorities.remove();

            this.queuedThreads[priority].remove(Thread.currentThread());
        }

        this.decrementCounter();
    }

    @Override
    public int getQueueLength() {
        return this.queuedPriorities.size();
    }

    @Override
    public boolean hasQueuedThreads() {
        return !this.queuedPriorities.isEmpty();
    }
}
