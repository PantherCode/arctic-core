package org.panthercode.arctic.core.concurrent.impl;

import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.priority.PriorityComparator;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by architect on 11.12.16.
 */
public class PriorityQueuedSemaphore extends AbstractSemaphore<Priority> {

    private Queue<Long>[] threadQueues;

    private Queue<Priority> priorityQueue;

    public PriorityQueuedSemaphore() {
        this(1);
    }

    public PriorityQueuedSemaphore(int capacity) {
        super(capacity);

        this.priorityQueue = new PriorityBlockingQueue<>(10, new PriorityComparator());

        this.threadQueues = new Queue[5];

        for (int i = 0; i < 5; i++) {
            this.threadQueues[i] = new LinkedBlockingQueue<>();
        }
    }

    @Override
    public synchronized void acquire() throws InterruptedException {
        this.acquire(Priority.NORMAL);
    }

    @Override
    public synchronized void acquire(Priority value) throws InterruptedException {
        if (this.counter() == 0) {
            long threadId = Thread.currentThread().getId();

            this.priorityQueue.add(value);

            this.threadQueues[value.priority() - 1].add(threadId);

            while (this.counter() == 0 || threadId != this.threadQueues[this.priorityQueue.peek().priority() - 1].peek()) {
                this.wait();
            }

            this.priorityQueue.remove();

            this.threadQueues[value.priority() - 1].remove();
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
    public int getQueueLength() {
        return 0;
    }

    @Override
    public boolean hasQueuedThreads() {
        return false;
    }


    public int size() {
        int size = 0;

        for (Queue<Long> queue : this.threadQueues) {
            size += queue.size();
        }

        return size;
    }
}
