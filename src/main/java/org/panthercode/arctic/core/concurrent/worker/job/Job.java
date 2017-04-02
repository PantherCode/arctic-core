package org.panthercode.arctic.core.concurrent.worker.job;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.identity.Identity;

/**
 * Created by architect on 02.04.17.
 */
public abstract class Job implements Runnable, Identifiable {

    private final Identity identity;

    private final Handler<Exception> exceptionHandler;

    public Job() {
        this.identity = Identity.fromAnnotation(this.getClass());

        this.exceptionHandler = null;
    }

    public Job(Identity identity) {
        this(identity, null);
    }

    public Job(Identity identity, Handler<Exception> exceptionHandler) {
        this.identity = ArgumentUtils.checkNotNull(identity, "identity");

        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public Identity identity() {
        return this.identity.copy();
    }

    public Handler<Exception> exceptionHandler() {
        return this.exceptionHandler;
    }

    public static Job create(Identity identity, Runnable runnable) {
        return Job.create(identity, runnable, null);
    }

    public static Job create(Identity identity, Runnable runnable, Handler<Exception> exceptionHandler) {
        return new RunnableJob(identity, runnable, exceptionHandler);
    }

    private static class RunnableJob extends Job {
        private final Runnable job;

        RunnableJob(Identity identity, Runnable job, Handler<Exception> handler) {
            super(identity, handler);

            this.job = ArgumentUtils.checkNotNull(job, "job");
        }

        @Override
        public void run() {
            this.job.run();
        }
    }
}
