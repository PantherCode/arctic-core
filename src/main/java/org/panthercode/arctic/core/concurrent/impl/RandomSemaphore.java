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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class RandomSemaphore extends AbstractSemaphore<Void> {

    private Random random;

    private List<Thread> queuedThreads;

    public RandomSemaphore() {
        this(1);
    }

    public RandomSemaphore(int capacity) {
        super(capacity);

        this.random = new Random(System.currentTimeMillis());

        this.queuedThreads = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized void acquire() throws Exception {
        this.acquire(null);
    }

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

    @Override
    public int getQueueLength() {
        return this.queuedThreads.size();
    }

    @Override
    public boolean hasQueuedThreads() {
        return !this.queuedThreads.isEmpty();
    }
}
