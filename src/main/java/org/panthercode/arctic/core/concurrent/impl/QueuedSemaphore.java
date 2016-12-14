package org.panthercode.arctic.core.concurrent.impl;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class QueuedSemaphore extends AbstractSemaphore<Void> {

    private Queue<Thread> queue;

    public QueuedSemaphore() {
        this(1);
    }

    public QueuedSemaphore(int capacity) {
        super(capacity);

        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public synchronized void acquire() throws InterruptedException {
        this.acquire(null);
    }

    @Override
    public synchronized void acquire(Void value) throws InterruptedException {
        if (this.counter() == 0) {
            this.queue.add(Thread.currentThread());

            while (this.counter() == 0 || !Thread.currentThread().equals(this.queue.peek())) {
                this.wait();
            }

            this.queue.remove();
        }

        this.decrementCounter();
    }

    @Override
    public int getQueueLength() {
        return this.queue.size();
    }

    @Override
    public boolean hasQueuedThreads() {
        return !this.queue.isEmpty();
    }
}
