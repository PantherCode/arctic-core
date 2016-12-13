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

    private final int allowedParalleledThreads;

    public AbstractSemaphore() {
        this(1);
    }

    public AbstractSemaphore(int allowedParalleledThreads) {
        ArgumentUtils.assertGreaterZero(allowedParalleledThreads, "allowed paralleled threads");

        this.allowedParalleledThreads = this.counter = allowedParalleledThreads;
    }

    @Override
    public int getActualThreadCount() {
        return this.allowedParalleledThreads - this.counter;
    }

    @Override
    public int getAllowedParalleledThreads() {
        return this.allowedParalleledThreads;
    }

    protected synchronized void incrementCounter() {
        this.counter++;
    }

    protected synchronized void decrementCounter() {
        this.counter--;
    }

    protected int counter() {
        return this.counter;
    }
}
