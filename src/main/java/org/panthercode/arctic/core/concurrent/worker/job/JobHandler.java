package org.panthercode.arctic.core.concurrent.worker.job;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Handler;

/**
 * Created by architect on 02.04.17.
 */
public final class JobHandler implements Handler<Job> {

    private boolean ignoreExceptions = false;

    public JobHandler() {
    }

    public JobHandler(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    public void ignoreExceptions(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    public boolean isIgnoreExceptions() {
        return this.ignoreExceptions;
    }

    @Override
    public void handle(Job job) {
        try {
            ArgumentUtils.checkNotNull(job, "job");

            job.run();
        } catch (Exception e) {
            if (!this.ignoreExceptions) {
                if (job.exceptionHandler() == null) {
                    throw new RuntimeException("While running the job handler an error is occurred.", e);
                } else {
                    try {
                        job.exceptionHandler().handle(e);
                    } catch (Exception ex) {
                        throw new RuntimeException("While running the exception handler an error is occurred.", ex);
                    }
                }
            }
        }
    }
}
