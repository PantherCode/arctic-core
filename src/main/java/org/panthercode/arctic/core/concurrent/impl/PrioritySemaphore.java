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

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class PrioritySemaphore extends AbstractSemaphore<Priority> {

    private Queue<Priority> queue;

    public PrioritySemaphore() {
        this(1);
    }

    public PrioritySemaphore(int capacity) {
        super(capacity);

        this.queue = new PriorityBlockingQueue<Priority>(10, new PriorityComparator());
    }

    @Override
    public synchronized void acquire()
            throws InterruptedException {
        this.acquire(Priority.NORMAL);
    }

    @Override
    public synchronized void acquire(Priority priority)
            throws InterruptedException {
        if (this.counter() == 0) {
            this.queue.add(priority);

            while (this.counter() == 0 || priority != this.queue.peek()) {
                this.wait();
            }

            this.queue.remove();
        }

        this.decrementCounter();
    }

    @Override
    public synchronized void release() {
        if (this.counter() == 0) {
            this.notifyAll();
        }

        this.incrementCounter();
    }
}
