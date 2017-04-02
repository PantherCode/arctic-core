package org.panthercode.arctic.core.concurrent.worker;

import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.priority.Priority;

import java.time.LocalDateTime;

/**
 * Created by architect on 27.03.17.
 */
public interface ProcessDetails {

    ProcessState state();

    Priority priority();

    boolean isInfinitely();

    boolean isReady();

    long delayTime();

    double progress();

    long value();

    long maximum();

    LocalDateTime created();

    LocalDateTime lastRun();

    long elapsedTime();

    long duration();
}
