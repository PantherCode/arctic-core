package org.panthercode.arctic.core.event;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by architect on 28.02.17.
 */
public class EventBus {
    private Queue<EventBusEntry> queue = new LinkedList<>();

    private boolean isRunning;

    private boolean ignoreExceptions;

    private static final Object lock = new Object();

    public EventBus() {
    }

    public void process(EventHandler handler, Object source, EventArgs e) {
        if (handler != null && e != null) {
            EventBusEntry newEntry = new EventBusEntry(handler, source, e);

            synchronized (lock) {
                this.queue.add(newEntry);

                if (this.queue.size() == 1) {
                    lock.notify();
                }
            }
        }
    }

    public synchronized void start() {
        this.start(false);
    }

    public void start(boolean ignoreExceptions) {
        synchronized (lock) {
            if (!this.isRunning) {
                this.isRunning = true;

                this.ignoreExceptions = ignoreExceptions;

                new Thread(() -> {
                    try {
                        EventBusEntry entry;

                        while (EventBus.this.isRunning()) {
                            synchronized (lock) {
                                while (EventBus.this.queue.isEmpty()) {
                                    lock.wait();

                                    if (!EventBus.this.isRunning) {
                                        return;
                                    }
                                }

                                entry = this.queue.remove();
                            }

                            entry.process();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        if (!EventBus.this.ignoreExceptions) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        }
    }

    public void stop() {
        synchronized (lock) {
            if (this.isRunning) {
                this.isRunning = false;

                if (this.queue.isEmpty()) {
                    lock.notify();
                }
            }
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    private class EventBusEntry {
        private final EventHandler handler;

        private final Object source;

        private final EventArgs args;

        EventBusEntry(EventHandler handler, Object source, EventArgs args) {
            this.handler = handler;

            this.source = source;

            this.args = args;
        }

        @SuppressWarnings("unchecked")
        public void process() {
            this.handler.handle(this.source, this.args);
        }
    }

}
