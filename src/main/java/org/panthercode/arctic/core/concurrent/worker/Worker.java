package org.panthercode.arctic.core.concurrent.worker;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.worker.job.Job;
import org.panthercode.arctic.core.concurrent.worker.trigger.TriggerBuilder;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.priority.Priority;
import org.panthercode.arctic.core.runtime.message.Message;
import org.panthercode.arctic.core.runtime.message.MessageResponse;
import org.panthercode.arctic.core.runtime.service.Service;
import org.panthercode.arctic.core.settings.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by architect on 27.03.17.
 */
@VersionInfo(major = 1, minor = 0)
@IdentityInfo(name = "Worker", group = "Concurrent")
public class Worker implements Service<Job> {

    /**
     *
     */
    public final static String CONTEXT_THREAD_COUNT = "thread.count";

    /**
     *
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     */
    private final static Object lock = new Object();

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
    private final Map<Identity, ProcessDetails> processes = new ConcurrentHashMap<>();

    /**
     *
     */
    private boolean isActive = false;

    /**
     *
     */
    private int threadCount;

    /**
     *
     */
    private Scheduler scheduler;

    /**
     * @param threadCount
     */
    public Worker(int threadCount) {
        this.threadCount = ArgumentUtils.checkGreaterZero(threadCount, "thread count");

        this.identity = Identity.fromAnnotation(this.getClass());

        this.version = Version.fromAnnotation(this.getClass());
    }

    @Override
    public boolean canActivate() {
        return true;
    }

    @Override
    public boolean canDeactivate() {
        return this.processes.isEmpty();
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void activate() {
        this.activate(this.threadCount);
    }

    public synchronized void activate(int threadCount) {
        if (!this.isActive) {
            this.isActive = true;

            this.threadCount = ArgumentUtils.checkGreaterZero(threadCount, "thread count");


        }
    }

    @Override
    public void activate(Context context) {
        ArgumentUtils.checkNotNull(context, "context");

        context.check(Worker.CONTEXT_THREAD_COUNT);

        int threadCount = context.getInteger(Worker.CONTEXT_THREAD_COUNT);

        this.activate(threadCount);
    }

    @Override
    public void deactivate() {
        synchronized (lock) {
            if (this.isActive) {
                this.isActive = false;


            }
        }
    }

    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    @Override
    public Version version() {
        return this.version.copy();
    }

    public void add(Job job) {
        this.add(job, TriggerBuilder.empty());
    }

    public void add(Job job, TriggerBuilder trigger) {
        ArgumentUtils.checkNotNull(job, "job");
        ArgumentUtils.checkNotNull(trigger, "trigger");


    }

    public ProcessState state(Identity identity) {
        if (this.processes.containsKey(identity)) {
            return this.processes.get(identity).state();
        }

        return null;
    }

    public ProcessDetails kill(Identity identity) {
        throw new NotImplementedException();
    }

    public Set<Identity> processIds() {
        return new HashSet<>(this.processes.keySet());
    }

    public Collection<ProcessDetails> processes() {
        return new ArrayList<>(this.processes.values());
    }

    public int size() {
        return this.processes.size();
    }

    public ProcessDetails processById(Identity identity) {
        if (this.processes.containsKey(identity)) {
            return this.processes.get(identity);
        }

        return null;
    }

    public Priority priority(Identity identity) {
        if (this.processes.containsKey(identity)) {
            return this.processes.get(identity).priority();
        }

        return null;
    }

    public boolean setPriority(Identity identity, Priority priority) {
        if (identity != null && priority != null) {
            if (this.processes.containsKey(identity)) {
                synchronized (lock) {
                    ProcessDetailsImpl details = (ProcessDetailsImpl) this.processes.get(identity);
                    details.setPriority(priority);
                    this.processes.put(identity, details);
                }

                return true;
            }
        }

        return false;
    }

    public boolean contains(Identity identity) {
        return this.processes.containsKey(identity);
    }

    @Override
    public <T extends Job> void process(Message<T> message) {

    }

    @Override
    public <T extends Job> void process(Message<T> message, Handler<MessageResponse<T>> messageResponseHandler) {

    }

    @Override
    public <T extends Job> void process(Message<T> message, Handler<MessageResponse<T>> messageResponseHandler, Handler<Exception> exceptionHandler) {

    }



    /*
     * helper classes
     */

    private class ProcessDetailsImpl implements ProcessDetails {

        private Priority priority;

        @Override
        public ProcessState state() {
            return null;
        }

        @Override
        public Priority priority() {
            return null;
        }

        @Override
        public boolean isInfinitely() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public long delayTime() {
            return 0;
        }

        @Override
        public double progress() {
            return 0;
        }

        @Override
        public long value() {
            return 0;
        }

        @Override
        public long maximum() {
            return 0;
        }

        @Override
        public LocalDateTime created() {
            return null;
        }

        @Override
        public LocalDateTime lastRun() {
            return null;
        }

        @Override
        public long elapsedTime() {
            return 0;
        }

        @Override
        public long duration() {
            return 0;
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }


    }

    private class Scheduler {
        /**
         *
         */
        private Thread schedulerThread;

        /**
         *
         */
        private ExecutorService executor;

        /**
         *
         */
        private long delayTime;

        /**
         *
         */
        private boolean isActive = false;

        Scheduler() {
        }

        void start(int threadCount) {
            if (!this.isActive) {
                this.isActive = true;

                logger.debug("Create new thread pool (thread count = {}).", threadCount);

                this.executor = Executors.newFixedThreadPool(threadCount);

                this.schedulerThread = new Thread(() -> {
                    while (Scheduler.this.isActive) {
                        for (ProcessDetails details : Worker.this.processes.values()) {
                            if (details.isReady() && details.state() == ProcessState.WAITING) {

                            }
                        }

                        try {
                            Thread.sleep(Scheduler.this.getDelayTime());
                        } catch (InterruptedException e) {
                            logger.debug("Scheduler was interrupt.");

                            return;
                        }
                    }
                });

                logger.debug("Starting scheduler thread.");

                this.schedulerThread.start();
            }
        }

        void stop() {
            if (this.isActive) {
                this.isActive = false;

                this.schedulerThread.interrupt();

                this.executor.shutdownNow();
            }
        }

        void setDelayTime(long delayTime) {
            this.delayTime = delayTime;
        }

        long getDelayTime() {
            return this.delayTime;
        }
    }
}
