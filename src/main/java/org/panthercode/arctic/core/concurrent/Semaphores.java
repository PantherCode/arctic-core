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

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.impl.PriorityQueuedSemaphore;
import org.panthercode.arctic.core.concurrent.impl.PrioritySemaphore;
import org.panthercode.arctic.core.concurrent.impl.QueuedSemaphore;
import org.panthercode.arctic.core.concurrent.impl.RandomSemaphore;
import org.panthercode.arctic.core.helper.priority.Priority;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class Semaphores {

    private Semaphores() {
    }

    public static Semaphore<Priority> createPriorityQueuedSemaphore() {
        return Semaphores.createPriorityQueuedSemaphore(1);
    }

    public static Semaphore<Priority> createPriorityQueuedSemaphore(int allowedParalleledThreads) {
        return new PriorityQueuedSemaphore(allowedParalleledThreads);
    }

    public static Semaphore<Priority> createPrioritySemaphore() {
        return Semaphores.createPrioritySemaphore(1);
    }

    public static Semaphore<Priority> createPrioritySemaphore(int allowedParalleledThreads) {
        return new PrioritySemaphore(allowedParalleledThreads);
    }

    public static Semaphore<Void> createQueuedSemaphore() {
        return Semaphores.createQueuedSemaphore(1);
    }

    public static Semaphore<Void> createQueuedSemaphore(int allowedParalleledThreads) {
        return new QueuedSemaphore(allowedParalleledThreads);
    }

    public static Semaphore<Void> createRandomSemaphore() {
        return Semaphores.createRandomSemaphore(1);
    }

    public static Semaphore<Void> createRandomSemaphore(int allowedParalleledThreads) {
        return new RandomSemaphore(allowedParalleledThreads);
    }

    public static <T> Runnable add(Runnable runnable, Semaphore<T> semaphore, T semaphoreValue) {
        ArgumentUtils.assertNotNull(runnable, "runnable");
        ArgumentUtils.assertNotNull(semaphore, "semaphore");

        return new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire(semaphoreValue);

                    runnable.run();

                    semaphore.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
