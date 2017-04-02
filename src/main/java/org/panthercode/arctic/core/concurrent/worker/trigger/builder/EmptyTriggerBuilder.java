package org.panthercode.arctic.core.concurrent.worker.trigger.builder;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.worker.trigger.ProcessUpdater;
import org.panthercode.arctic.core.concurrent.worker.trigger.Trigger;
import org.panthercode.arctic.core.concurrent.worker.trigger.TriggerBuilder;
import org.panthercode.arctic.core.processing.priority.Priority;

/**
 * Created by architect on 29.03.17.
 */
public class EmptyTriggerBuilder extends TriggerBuilder {

    private Priority priority = Priority.NORMAL;

    public EmptyTriggerBuilder priority(Priority priority) {
        this.priority = ArgumentUtils.checkNotNull(priority, "priority");

        return this;
    }

    @Override
    public Trigger build() {
        return new Trigger(this.priority, false, 0L, 1L, new EmptyUpdater());
    }

    private class EmptyUpdater implements ProcessUpdater {

        private long value = 0;

        public EmptyUpdater() {
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
