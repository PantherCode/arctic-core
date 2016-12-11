package org.panthercode.arctic.core.concurrent.impl;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class QueuedSemaphore extends AbstractSemaphore<Void> {

    Queue<Long> queue = new LinkedBlockingQueue<>();

    public QueuedSemaphore() {
        super();
    }

    public QueuedSemaphore(int capacity) {
        super(capacity);
    }

    @Override
    public synchronized void acquire() throws InterruptedException {
        this.acquire(null);
    }

    @Override
    public synchronized void acquire(Void value) throws InterruptedException {
        if (this.counter() == 0) {
            long threadId = Thread.currentThread().getId();

            this.queue.add(threadId);

            while (this.counter() == 0 || threadId != this.queue.peek()) {
                this.wait();
            }

            this.queue.remove();
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

    @Override
    public int size() {
        return this.queue.size();
    }
}
