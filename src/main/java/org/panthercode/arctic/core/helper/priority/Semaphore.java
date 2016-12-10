package org.panthercode.arctic.core.helper.priority;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public class Semaphore {

    private static int INITIAL_CAPACITY = 10;

    private int counter = 0;

    private int capacity = 0;

    private Queue<Priority> queue;

    public Semaphore(int capacity) {
        ArgumentUtils.assertGreaterZero(capacity, "capacity");

        this.counter = this.capacity = capacity;

        this.queue = new PriorityBlockingQueue<>(INITIAL_CAPACITY, new PriorityComparator());
    }

    public synchronized void acquire() throws InterruptedException {
        this.acquire(Priority.NORMAL);
    }

    public synchronized void acquire(Priority priority) throws InterruptedException {
        if (counter == 0) {
            this.queue.add(priority);

            while (counter == 0 || priority != this.queue.peek()) {
                this.wait();
            }

            this.queue.remove();
        }

        this.counter--;
    }

    public synchronized void release() {
        if (counter == 0) {
            this.notifyAll();
        }

        this.counter++;
    }

    public int capacity() {
        return this.capacity;
    }

    public int counter() {
        return this.counter;
    }
}
