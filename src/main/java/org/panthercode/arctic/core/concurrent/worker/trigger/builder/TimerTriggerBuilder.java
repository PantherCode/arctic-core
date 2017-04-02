package org.panthercode.arctic.core.concurrent.worker.trigger.builder;

import com.google.common.base.Preconditions;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.worker.trigger.ProcessUpdater;
import org.panthercode.arctic.core.concurrent.worker.trigger.Trigger;
import org.panthercode.arctic.core.concurrent.worker.trigger.TriggerBuilder;
import org.panthercode.arctic.core.processing.priority.Priority;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Created by architect on 29.03.17.
 */
public class TimerTriggerBuilder extends TriggerBuilder {

    private LocalDateTime until = null;

    private long maximum = 0L;

    private long delayTimeInMillis = 0L;

    private Priority priority = Priority.NORMAL;

    private boolean isInfinitely = false;

    public TimerTriggerBuilder() {
    }

    public TimerTriggerBuilder duration(long duration, TimeUnit unit) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");
        ArgumentUtils.checkNotNull(unit, "time unit");

        this.maximum = TimeUnit.MILLISECONDS.convert(duration, unit);
        this.until = null;

        return this;
    }

    public TimerTriggerBuilder until(LocalDateTime until) {
        ArgumentUtils.checkNotNull(until, "until");
        Preconditions.checkArgument(LocalDateTime.now().isAfter(until), "The given date is before now.");

        this.maximum = -1L;
        this.until = until;

        return this;
    }

    public TimerTriggerBuilder delayTime(long durationInMillis) {
        this.delayTimeInMillis = ArgumentUtils.checkGreaterOrEqualsZero(durationInMillis, "duration");

        return this;
    }

    public TimerTriggerBuilder delayTime(long duration, TimeUnit unit) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");
        ArgumentUtils.checkNotNull(unit, "time unit");

        this.delayTimeInMillis = TimeUnit.MILLISECONDS.convert(duration, unit);

        return this;
    }

    public TimerTriggerBuilder isInfinitely() {
        this.isInfinitely = true;

        return this;
    }

    public TimerTriggerBuilder priority(Priority priority) {
        this.priority = ArgumentUtils.checkNotNull(priority, "priority");

        return this;
    }

    @Override
    public Trigger build() {
        if (this.until != null && this.maximum == -1) {
            this.maximum = Math.max(0L, this.until.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis());
        }

        return new Trigger(this.priority, this.isInfinitely, this.delayTimeInMillis, this.maximum, new TimerUpdater());
    }

    private class TimerUpdater implements ProcessUpdater {

        private long value = 0;

        private long timestamp = 0;

        public TimerUpdater() {
            this.reset();
        }

        @Override
        public long value() {
            return this.value;
        }

        @Override
        public synchronized void update() {
            this.value += System.currentTimeMillis() - timestamp;

            this.timestamp = System.currentTimeMillis();
        }

        @Override
        public synchronized void reset() {
            this.value = System.currentTimeMillis();
        }
    }
}
