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
import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.priority.PriorityObject;
import org.panthercode.arctic.core.helper.priority.PriorityObjectComparator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class Worker implements Runnable {

    private int threadCount;

    private List<Runnable> threads;

    private Queue<PriorityObject<Runnable>> queue;

    private ExecutorService threadPool;

    public Worker(int threadCount) {
        ArgumentUtils.assertGreaterZero(threadCount, "thread count");

        this.threadCount = threadCount;

        this.queue = new PriorityBlockingQueue<>(10, new PriorityObjectComparator<>());

        this.threads = Collections.synchronizedList(new ArrayList<>());
    }

    public static Worker start(int threadCount) {
        Worker worker = new Worker(threadCount);

        new Thread(worker).start();

        return worker;
    }

    public int threadCount() {
        return this.threadCount;
    }

    public int actualThreadCount() {
        return this.threads.size();
    }

    public synchronized void add(Runnable runnable) {
        this.add(runnable, Priority.NORMAL);
    }

    public synchronized void add(Runnable runnable, Priority priority) {
        if (runnable != null && priority != null) {
            this.queue.add(new PriorityObject<>(runnable, priority));

            if (this.isRunning() && this.actualThreadCount() < this.threadCount()) {
                this.notify();
            }
        }
    }

    public synchronized void remove(Runnable runnable) {
        if (runnable != null) {
            for (Iterator<PriorityObject<Runnable>> iterator = this.queue.iterator();
                 iterator.hasNext(); ) {
                if (iterator.next().getContent().equals(runnable)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public int getQueueLength() {
        return this.queue.size();
    }

    public boolean hasQueuedElements() {
        return !this.queue.isEmpty();
    }

    public synchronized List<Runnable> threadPool() {
        return new ArrayList<>(this.threads);
    }

    public synchronized Queue<PriorityObject<Runnable>> queue() {
        return new PriorityBlockingQueue<>(this.queue);
    }

    public boolean isRunning() {
        return this.threadPool != null && !this.threadPool.isShutdown();
    }

    @Override
    public synchronized void run() {
        if (!this.isRunning()) {
            this.threadPool = Executors.newFixedThreadPool(this.threadCount);

            while (this.isRunning()) {
                try {
                    this.fillThreadPool();

                    while (this.isRunning() &&
                            (this.threadCount == this.actualThreadCount() || !this.hasQueuedElements())) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void shutdown() {
        this.threadPool.shutdown();
    }

    public synchronized List<Runnable> shutdownNow() {
        return this.threadPool.shutdownNow();
    }

    private synchronized void fillThreadPool() {
        while (this.hasQueuedElements() &&
                this.isRunning() &&
                this.actualThreadCount() < this.threadCount()) {
            final PriorityObject<Runnable> actualObject = this.queue.remove();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    actualObject.getContent().run();

                    threads.remove(this);

                    fillThreadPool();
                }
            };

            this.threads.add(runnable);

            this.threadPool.execute(runnable);
        }
    }
}
