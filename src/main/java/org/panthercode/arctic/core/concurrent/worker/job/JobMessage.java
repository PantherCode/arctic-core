package org.panthercode.arctic.core.concurrent.worker.job;

import org.panthercode.arctic.core.concurrent.worker.trigger.TriggerBuilder;
import org.panthercode.arctic.core.runtime.message.SimpleMessage;

/**
 * Created by architect on 02.04.17.
 */
public class JobMessage extends SimpleMessage<Job> {

    private TriggerBuilder builder;

    public JobMessage(Job job) {
        this(job, null);
    }

    public JobMessage(Job job, TriggerBuilder builder) {
        this.builder = builder;
    }

    public TriggerBuilder builder() {
        return this.builder;
    }
}
