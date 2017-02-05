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
 * The <tt>Worker</tt> is used to handle a thread pool to process a bunch of runnable objects. Queue runnable objects
 * are executed by a <tt>ExecutorService</tt> class.
 * <p>
 * Foreach runnable object you add to queue an corresponding thread object is created and executed. Before execution
 * the runnable object is extended with a routine. After executing objects <tt>run()</tt> method it will take the next
 * element from queue and add it to thread pool. Therefore the thread pool is never getting empty if any queued element
 * exits.
 * <p>
 * Because it's very useful to run a <tt>Worker</tt> class in extra thread, you can call <tt>start()</tt> method:
 * <pre>
 * Worker worker = Worker.start(1); //Starts a worker in extra thread with thread pool size with one thread
 *
 * //instead of
 * Worker worker = new Worker(1);
 * Thread thread = new Thread(worker);
 * thread.start();
 * </pre>
 *
 * @author PantherCode
 * @see Task
 * @since 1.0
 */
public class Worker implements Runnable {

    /**
     * number of working threads
     */
    private int threadCount;

    /**
     * collection of current running threads
     */
    private List<Runnable> threads;

    /**
     * queue of runnable objects waiting for executing
     */
    private Queue<PriorityObject<Runnable>> queue;

    /**
     * thread pool to handle execution
     */
    private ExecutorService threadPool;

    /**
     * Constructor
     *
     * @param threadCount number of working threads
     */
    public Worker(int threadCount) {
        ArgumentUtils.checkGreaterZero(threadCount, "thread count");

        this.threadCount = threadCount;

        this.queue = new PriorityBlockingQueue<>(10, new PriorityObjectComparator<>());

        this.threads = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Creates a new instance of <tt>Worker</tt> class and starts it in a extra thread.
     *
     * @param threadCount number of working threads
     * @return Returns a new instance of <tt>Working</tt> class.
     */
    public static Worker start(int threadCount) {
        Worker worker = new Worker(threadCount);

        new Thread(worker).start();

        return worker;
    }

    /**
     * Returns the maximal number of working threads.
     *
     * @return Returns the maximal number of working  threads.
     */
    public int threadCount() {
        return this.threadCount;
    }

    /**
     * Returns the actual number of running working threads.
     *
     * @return Returns the actual number of running working threads.
     */
    public int actualThreadCount() {
        return this.threads.size();
    }

    /**
     * Adds a new runnable object to queue. The object is added with <tt>Priority.NORMAL</tt>. This function is
     * <tt>synchronized</tt>.
     *
     * @param runnable runnable object for execution
     */
    public synchronized void add(Runnable runnable) {
        this.add(runnable, Priority.NORMAL);
    }

    /**
     * Adds a new runnable object to queue with custom toInteger. This function is <tt>synchronized</tt>.
     *
     * @param runnable runnable object for execution
     * @param priority toInteger to run the object
     */
    public synchronized void add(Runnable runnable, Priority priority) {
        if (runnable != null && priority != null) {
            this.queue.add(new PriorityObject<>(runnable, priority));

            if (this.isRunning() && this.actualThreadCount() < this.threadCount()) {
                this.notify();
            }
        }
    }

    /**
     * Removes a given runnable object from queue. This function is synchronized.
     *
     * @param runnable object to remove
     */
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

    /**
     * Returns the size of queue.
     *
     * @return Returns the size of queue.
     */
    public int getQueueLength() {
        return this.queue.size();
    }

    /**
     * Returns a flag indicating whether the queue contains any element or not.
     *
     * @return Returns <tt>true</tt> if queue size is greater zero; Ohterwise <tt>false</tt>.
     */
    public boolean hasQueuedElements() {
        return !this.queue.isEmpty();
    }

    /**
     * Returns a list containing all running threads. Attention: Be careful with handling this list. Changes in returned
     * list object also have effects to internal list.
     *
     * @return Returns a list with all current running threads.
     */
    public List<Runnable> threadPool() {
        return new ArrayList<>(this.threads);
    }

    /**
     * Returns a queue containing waiting runnable objects. Attention: Be careful with handling this queue. Changes in
     * returned queue object also have effects to internal queue.
     *
     * @return Returns a queue containing waiting runnable objects.
     */
    public Queue<PriorityObject<Runnable>> queue() {
        return new PriorityBlockingQueue<>(this.queue);
    }

    /**
     * Returns a flag indicating whether the <tt>Worker</tt> class is running or not.
     *
     * @return Returns <tt>true</tt> if <tt>Worker</tt> if internal thread pool is running; Otherwise <tt>false</tt>.
     */
    public boolean isRunning() {
        return this.threadPool != null && !this.threadPool.isShutdown();
    }

    /**
     * Start the internals thread  pool and begin to execute objects from queue.
     */
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

    /**
     * Shutdown the thread pool. All current running threads are executed until finish, but no new threads are accepted.
     */
    public synchronized void shutdown() {
        this.threadPool.shutdown();
    }

    /**
     * Initialize a hard shutdown by returning all current running and waiting threads.
     *
     * @return Returns a list with all threads in thread pool.
     */
    public synchronized List<Runnable> shutdownNow() {
        return this.threadPool.shutdownNow();
    }

    /**
     * This helper function organize the thread pool filling process. While maximal allowed thread count of thread pool
     * is less than actual one, this function will add elements from queue to thread pool.
     */
    private synchronized void fillThreadPool() {
        while (this.hasQueuedElements() &&
                this.isRunning() &&
                this.actualThreadCount() < this.threadCount()) {
            final PriorityObject<Runnable> actualObject = this.queue.remove();

            /*
             * Each runnable object starts after its finish another one from queue as a self-preserving process.
             */
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
