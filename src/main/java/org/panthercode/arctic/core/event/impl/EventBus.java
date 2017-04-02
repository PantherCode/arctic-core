package org.panthercode.arctic.core.event.impl;

import org.panthercode.arctic.core.event.EventArgs;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.runtime.message.Message;
import org.panthercode.arctic.core.runtime.message.MessageHandler;
import org.panthercode.arctic.core.runtime.message.MessageResponse;
import org.panthercode.arctic.core.runtime.service.Service;
import org.panthercode.arctic.core.settings.Context;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by architect on 28.02.17.
 */
@IdentityInfo(name = "EventBus")
@VersionInfo(major = 1, minor = 0)
public class EventBus implements Service<EventArgs> {
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
    private final Queue<EventBusJob> queue;

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
                this.isActive = true;

                this.thread = new Thread(new EventBusScheduler());

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

                if (this.queue.isEmpty()) {
                    LOCK.notify();
                }

                this.thread = null;
            }
        }
    }

    @Override
    public <T extends EventArgs> void process(Message<T> message) {
        this.process(message, null);
    }

    @Override
    public <T extends EventArgs> void process(Message<T> message, Handler<MessageResponse<T>> messageResponseHandler) {
        this.process(message, messageResponseHandler, null);
    }

    @Override
    public <T extends EventArgs> void process(Message<T> message, Handler<MessageResponse<T>> messageResponseHandler, Handler<Exception> exceptionHandler) {
        if (message != null) {
            synchronized (LOCK) {
                this.queue.add(new EventBusJob(message, messageResponseHandler, exceptionHandler));

                if (this.queue.size() == 1) {
                    LOCK.notify();
                }
            }
        }
    }

    private class EventBusJob<T> {
        private Message<EventArgs> message;

        private Handler<MessageResponse<EventArgs>> responseHandler;

        private Handler<Exception> exceptionHandler;

        @SuppressWarnings("unchecked")
        EventBusJob(Message message, Handler<MessageResponse<EventArgs>> responseHandler, Handler<Exception> exceptionHandler) {
            this.message = message;
            this.responseHandler = responseHandler;
            this.exceptionHandler = exceptionHandler;
        }

        Message message() {
            return this.message;
        }

        Handler<MessageResponse<EventArgs>> responseHandler() {
            return this.responseHandler;
        }

        Handler<Exception> exceptionHandler() {
            return this.exceptionHandler;
        }
    }

    /**
     *
     */
    private class EventBusScheduler implements Runnable {

        MessageHandler handler = new MessageHandler();

        @Override
        @SuppressWarnings("unchecked")
        public void run() {
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

                    EventBusJob actualJob = EventBus.this.queue.remove();
                    try {
                        handler.handle(actualJob.message, actualJob.responseHandler, actualJob.exceptionHandler);
                    } catch (Exception e) {
                        //TODO: implement
                    }
                }
            }
        }
    }
}