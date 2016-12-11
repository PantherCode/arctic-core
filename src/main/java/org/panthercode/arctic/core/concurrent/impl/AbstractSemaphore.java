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
 * TODO: documentation
 *
 * @author PantherCode
 */
public abstract class AbstractSemaphore<T> implements Semaphore<T> {

    private int counter = 0;

    private int capacity = 0;

    public AbstractSemaphore() {
        this(1);
    }

    public AbstractSemaphore(int capacity) {
        ArgumentUtils.assertGreaterZero(capacity, "capacity");

        this.capacity = this.counter = capacity;
    }

    public abstract void acquire() throws InterruptedException;

    public abstract void acquire(T value) throws InterruptedException;

    public abstract void release();

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int counter() {
        return this.counter;
    }

    @Override
    public abstract int size();

    protected synchronized void incrementCounter() {
        this.counter++;
    }

    protected synchronized void decrementCounter() {
        this.counter--;
    }

    protected synchronized void setCounter(int value) {
        ArgumentUtils.assertGreaterZero(value, "counter");

        this.counter = value;
    }
}
