package org.panthercode.arctic.core.concurrent.worker.trigger;

/**
 * Created by architect on 30.03.17.
 */
public interface ProcessUpdater {

    long value();

    void update();

    void reset();
}
