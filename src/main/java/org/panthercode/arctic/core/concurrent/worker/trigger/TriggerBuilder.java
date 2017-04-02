package org.panthercode.arctic.core.concurrent.worker.trigger;

import org.panthercode.arctic.core.concurrent.worker.trigger.builder.CounterTriggerBuilder;
import org.panthercode.arctic.core.concurrent.worker.trigger.builder.EmptyTriggerBuilder;
import org.panthercode.arctic.core.concurrent.worker.trigger.builder.TimerTriggerBuilder;

/**
 * Created by architect on 29.03.17.
 */
public abstract class TriggerBuilder {

    public abstract Trigger build();

    public static EmptyTriggerBuilder empty() {
        return new EmptyTriggerBuilder();
    }

    public static CounterTriggerBuilder counter() {
        return new CounterTriggerBuilder();
    }

    public static TimerTriggerBuilder timer() {
        return new TimerTriggerBuilder();
    }
}
