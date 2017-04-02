package org.panthercode.arctic.core.concurrent.worker.trigger.builder;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.worker.trigger.ProcessUpdater;
import org.panthercode.arctic.core.concurrent.worker.trigger.Trigger;
import org.panthercode.arctic.core.concurrent.worker.trigger.TriggerBuilder;
import org.panthercode.arctic.core.processing.priority.Priority;

import java.util.concurrent.TimeUnit;

/**
 * Created by architect on 29.03.17.
 */
public class CounterTriggerBuilder extends TriggerBuilder {

    private long maximum = 0L;

    private long delayTimeInMillis = 0L;

    private Priority priority = Priority.NORMAL;

    private boolean isInfinitely = false;

    public CounterTriggerBuilder() {
    }

    public CounterTriggerBuilder maximum(long value) {
        this.maximum = ArgumentUtils.checkGreaterZero(value, "maximum");

        return this;
    }

    public CounterTriggerBuilder delayTime(long durationInMillis) {
        this.delayTimeInMillis = ArgumentUtils.checkGreaterOrEqualsZero(durationInMillis, "duration");

        return this;
    }

    public CounterTriggerBuilder delayTime(long duration, TimeUnit unit) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");
        ArgumentUtils.checkNotNull(unit, "time unit");

        this.delayTimeInMillis = TimeUnit.MILLISECONDS.convert(duration, unit);

        return this;
    }

    public CounterTriggerBuilder isInfinitely() {
        this.isInfinitely = true;

        return this;
    }

    public CounterTriggerBuilder priority(Priority priority) {
        this.priority = ArgumentUtils.checkNotNull(priority, "priority");

        return this;
    }

    @Override
    public Trigger build() {
        return new Trigger(this.priority, this.isInfinitely, this.delayTimeInMillis, this.maximum, new CounterUpdater());
    }

    private class CounterUpdater implements ProcessUpdater {

        private long value = 0;

        public CounterUpdater() {
            this.reset();
        }

        @Override
        public long value() {
            return this.value;
        }

        @Override
        public synchronized void update() {
            this.value++;
        }

        @Override
        public synchronized void reset() {
            this.value = 0;
        }
    }
}
