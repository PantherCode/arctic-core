package org.panthercode.arctic.core.concurrent;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.priority.PriorityRunnable;
import org.panthercode.arctic.core.helper.priority.PriorityRunnableComparator;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class Worker implements Runnable {

    private boolean isRunning;

    private int currentThreadCount;

    private int maximalThreadCount;

    private Queue<PriorityRunnable> queue;

    private Semaphore<Priority> semaphore;

    public Worker(int maximalThreadCount) {
        ArgumentUtils.assertNotNull(semaphore, "semaphore");

        this.setMaximalThreadCount(maximalThreadCount);

        this.semaphore = Semaphores.createPriorityQueuedSemaphore(maximalThreadCount);

        this.queue = new PriorityBlockingQueue<>(10, new PriorityRunnableComparator());
    }

    public void setMaximalThreadCount(int count) {
        ArgumentUtils.assertGreaterZero(count, "count");

        this.maximalThreadCount = count;

        //TODO: set capacity of semaphores
    }

    public int getMaximalThreadCount() {
        return this.maximalThreadCount;
    }

    public synchronized void add(PriorityRunnable runnable) {
        this.queue.add(runnable);

        if (this.queue.size() == 1) {
            this.notify();
        }
    }

    public synchronized void remove(PriorityRunnable runnable) {
        this.queue.remove(runnable);
    }

    public synchronized void pause()
            throws InterruptedException {
        if (this.isRunning) {
            this.isRunning = false;

            this.wait();
        }
    }

    public synchronized void clear() {
        this.queue.clear();
    }

    /*public boolean isRunning() {
        return this.isRunning;
    }*/

    public int size() {
        return this.queue.size();
    }


    private synchronized void createThreads() {
        while (!queue.isEmpty() && this.currentThreadCount < this.maximalThreadCount) {
            PriorityRunnable priorityRunnable = this.queue.remove();

            Runnable runnable = Semaphores.addSemaphore(priorityRunnable, this.semaphore, priorityRunnable.priority());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    runnable.run();

                    createThreads();
                }
            }).start();
        }
    }

    @Override
    public synchronized void run() {
        while (true) {
            while (this.queue.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            this.createThreads();
        }
    }
}
