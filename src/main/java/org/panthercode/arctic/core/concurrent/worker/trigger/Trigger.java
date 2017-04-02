package org.panthercode.arctic.core.concurrent.worker.trigger;

import org.panthercode.arctic.core.processing.priority.Priority;

/**
 * Created by architect on 27.03.17.
 */
public class Trigger {

    private Priority priority;
    private boolean isInfinitely;
    private long delayTimeInMillis;
    private long maximum;
    private ProcessUpdater updater;

    public Trigger(Priority priority,
                   boolean isInfinitely,
                   long delayTimeInMillis,
                   long maximum,
                   ProcessUpdater updater) {
        this.priority = priority;
        this.isInfinitely = isInfinitely;
        this.delayTimeInMillis = delayTimeInMillis;
        this.maximum = maximum;
        this.updater = updater;
    }

    public Priority priority() {
        return this.priority;
    }

    public boolean isInfinitely() {
        return this.isInfinitely;
    }

    public long delayTime() {
        return this.delayTimeInMillis;
    }

    public long maximum() {
        return this.maximum;
    }

    public ProcessUpdater updater() {
        return this.updater;
    }
}
