package org.panthercode.arctic.core.concurrent.impl;

import org.panthercode.arctic.core.helper.priority.Priority;
import org.panthercode.arctic.core.helper.priority.PriorityComparator;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class PriorityQueuedSemaphore extends AbstractSemaphore<Priority> {

    private Queue<Thread>[] queuedThreads;

    private Queue<Priority> queuedPriorities;

    public PriorityQueuedSemaphore() {
        this(1);
    }

    public PriorityQueuedSemaphore(int capacity) {
        super(capacity);

        this.queuedPriorities = new PriorityBlockingQueue<>(10, new PriorityComparator());

        this.queuedThreads = new Queue[5];

        for (int i = 0; i < 5; i++) {
            this.queuedThreads[i] = new LinkedBlockingQueue<>();
        }
    }

    @Override
    public synchronized void acquire() throws InterruptedException {
        this.acquire(Priority.NORMAL);
    }

    @Override
    public synchronized void acquire(Priority value) throws InterruptedException {
        if (this.counter() == 0) {
            this.queuedPriorities.add(value);

            this.queuedThreads[value.priority() - 1].add(Thread.currentThread());

            while (this.counter() == 0 ||
                    !Thread.currentThread().equals(this.queuedThreads[this.queuedPriorities.peek().priority() - 1].peek())) {
                this.wait();
            }

            this.queuedThreads[value.priority() - 1].remove();

            this.queuedPriorities.remove();
        }

        this.decrementCounter();
    }

    @Override
    public int getQueueLength() {
        return this.queuedPriorities.size();
    }

    @Override
    public boolean hasQueuedThreads() {
        return !this.queuedPriorities.isEmpty();
    }
}
