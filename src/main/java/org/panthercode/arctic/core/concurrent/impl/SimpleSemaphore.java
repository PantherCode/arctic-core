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

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class SimpleSemaphore extends AbstractSemaphore<Void> {

    public SimpleSemaphore() {
        super();
    }

    public SimpleSemaphore(int capacity) {
        super(capacity);
    }

    @Override
    public void acquire()
            throws InterruptedException {
        this.acquire(null);
    }

    @Override
    public synchronized void acquire(Void value)
            throws InterruptedException {
        while (this.counter() == 0) {
            this.wait();
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
