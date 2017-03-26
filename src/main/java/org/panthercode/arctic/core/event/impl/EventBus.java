package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.runtime.Message;
import org.panthercode.arctic.core.runtime.Service;
import org.panthercode.arctic.core.settings.Context;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by architect on 28.02.17.
 */
@IdentityInfo(name = "EventBus")
@VersionInfo(major = 1, minor = 0)
public class EventBus implements Service {
    /**
     *
     */
    private static final Object LOCK = new Object();

    /**
     *
     */
    private boolean isActive;

    /**
     *
     */
    private final Identity identity;

    /**
     *
     */
    private final Version version;

    /**
     *
     */
    private final Queue<Message> queue;

    /**
     *
     */
    private Thread thread;

    /**
     *
     */
    public EventBus() {
        this.isActive = false;

        this.identity = Identity.fromAnnotation(this.getClass());

        this.version = Version.fromAnnotation(this.getClass());

        this.queue = new LinkedList<>();
    }

    /**
     * @return
     */
    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    /**
     * @return
     */
    @Override
    public Version version() {
        return this.version.copy();
    }

    /**
     * @return
     */
    @Override
    public boolean canActivate() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean canDeactivate() {
        return this.queue.isEmpty();
    }

    /**
     * @return
     */
    @Override
    public boolean isActive() {
        return this.isActive;
    }

    /**
     *
     */
    @Override
    public void activate() {
        this.activate(null);
    }

    /**
     * @param context
     */
    @Override
    public void activate(Context context) {
        synchronized (LOCK) {
            if (!this.isActive) {
                this.thread = new Thread(new EventBusRunnable());

                thread.start();
            }
        }
    }

    /**
     *
     */
    @Override
    public void deactivate() {
        synchronized (LOCK) {
            if (this.isActive) {
                this.isActive = false;
                this.thread = null;

                if (this.queue.isEmpty()) {
                    LOCK.notify();
                }
            }
        }
    }

    @Override
    public <T> void process(Message<T> message) {
        if (message != null) {
            synchronized (LOCK) {
                this.queue.add(message);

                if (this.queue.size() == 1) {
                    LOCK.notify();
                }
            }
        }
    }

    /**
     *
     */
    private class EventBusRunnable implements Runnable {
        @Override
        public void run() {
            Message actualMessage;

            while (EventBus.this.isActive) {
                synchronized (LOCK) {
                    while (EventBus.this.queue.isEmpty()) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }

                    if (!EventBus.this.isActive) {
                        return;
                    }

                    actualMessage = EventBus.this.queue.remove();
                }

                actualMessage.consume();
            }
        }
    }
}
