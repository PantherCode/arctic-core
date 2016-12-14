package org.panthercode.arctic.core.concurrent;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.priority.PriorityObject;
import org.panthercode.arctic.core.helper.priority.PriorityObjectComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class Worker implements Runnable {

    private boolean isRunning = false;

    private boolean isStarted = false;

    private Semaphore<Priority> semaphore;

    private Queue<PriorityObject<Runnable>> queue;

    private List<Thread> threads;

    public Worker(Semaphore<Priority> semaphore) {
        ArgumentUtils.assertNotNull(semaphore, "semaphore");

        this.semaphore = semaphore;

        this.queue = new PriorityBlockingQueue<>(10, new PriorityObjectComparator<>());

        this.threads = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void add(Runnable runnable) {
        this.add(runnable, Priority.NORMAL);
    }

    public synchronized void add(Runnable runnable, Priority priority) {
        if (runnable != null && priority != null) {
            this.queue.add(new PriorityObject<>(runnable, priority));

            if (this.isRunning && this.semaphore.getActualThreadCount() < this.getAllowedParalleledThreads()) {
                this.notifyAll();
            }
        }
    }

    public synchronized void remove(Runnable runnable) {
        this.remove(runnable);
    }

    public int getActualThreadCount() {
        return this.semaphore.getActualThreadCount();
    }

    public int getAllowedParalleledThreads() {
        return this.semaphore.getAllowedParalleledThreads();
    }

    public int getQueueLength() {
        return this.queue.size();
    }

    public boolean hasQueuedElements() {
        return !this.queue.isEmpty();
    }

    public synchronized List<Thread> threads() {
        return new ArrayList<>(this.threads);
    }

    public synchronized Queue<PriorityObject<Runnable>> queue() {
        return new PriorityBlockingQueue<>(this.queue);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public synchronized void fillThreadPool() {
        while (this.hasQueuedElements() && !this.semaphore.hasQueuedThreads() && this.isRunning && this.isStarted) {
            final PriorityObject<Runnable> actualObject = this.queue.remove();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Semaphores.add(actualObject.getContent(), semaphore, actualObject.getPriority()).run();

                    fillThreadPool();

                    threads.remove(Thread.currentThread());
                }
            };

            Thread thread = new Thread(runnable);

            this.threads.add(thread);

            thread.start();
        }
    }

    @Override
    public synchronized void run() {
        if (!this.isStarted) {
            this.isStarted = true;
            this.isRunning = true;

            while (this.isStarted) {
                try {
                    this.fillThreadPool();

                    while (semaphore.getActualThreadCount() == semaphore.getAllowedParalleledThreads() || !this.isRunning) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            if (!this.isRunning) {
                this.isRunning = true;

                this.notifyAll();
            }
        }
    }

    public synchronized void stop() {
        this.isRunning = false;
    }

    public synchronized void shutdown() {
        this.isStarted = false;
    }

    public static Worker start(Semaphore<Priority> semaphore) {
        ArgumentUtils.assertNotNull(semaphore, "semaphore");

        Worker worker = new Worker(semaphore);

        new Thread(worker).start();

        return worker;
    }
}
