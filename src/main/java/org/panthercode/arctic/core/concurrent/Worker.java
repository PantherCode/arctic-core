package org.panthercode.arctic.core.concurrent;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.priority.PriorityRunnable;
import org.panthercode.arctic.core.helper.priority.PriorityRunnableComparator;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class Worker<T> implements Runnable{

    private boolean isRunning;

    private int threadCount;

    private Queue<PriorityRunnable> queue;

    private Semaphore<T> semaphore;

    public Worker(Semaphore<T> semaphore, int threadCount) {
        ArgumentUtils.assertNotNull(semaphore, "semaphore");

        this.setThreadCount(threadCount);

        this.queue = new PriorityBlockingQueue<>(10, new PriorityRunnableComparator());
    }

    public void setThreadCount(int count) {
        ArgumentUtils.assertGreaterZero(threadCount, "thread count");

        this.threadCount = count;
    }

    public int getThreadCount() {
        return this.threadCount;
    }

    public synchronized void add(PriorityRunnable runnable) {
        this.queue.add(runnable);
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

    public boolean isRunning() {
        return this.isRunning;
    }

    public int size() {
        return this.size();
    }

    @Override
    public void run() {

    }


}
