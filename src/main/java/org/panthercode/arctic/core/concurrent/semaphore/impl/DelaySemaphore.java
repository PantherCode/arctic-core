package org.panthercode.arctic.core.concurrent.semaphore.impl;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

/**
 * Created by architect on 26.04.17.
 */
public class DelaySemaphore extends QueuedSemaphore {

    private long delayTimeInMillis;

    public DelaySemaphore() {
        this(1_000);
    }

    public DelaySemaphore(long delayTimeInMillis) {
        this(delayTimeInMillis, 1);
    }

    public DelaySemaphore(long delayTimeInMillis, int allowedParalleledThreads) {
        super(allowedParalleledThreads);

        this.delayTimeInMillis = ArgumentUtils.checkGreaterZero(delayTimeInMillis, "delay time");
    }

    @Override
    public synchronized void release() {
        super.release();

        try {
            Thread.sleep(this.delayTimeInMillis);
        } catch (InterruptedException ignored) {
        }
    }
}
