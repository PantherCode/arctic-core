package org.panthercode.arctic.core.concurrent;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.concurrent.semaphore.Semaphore;
import org.panthercode.arctic.core.concurrent.semaphore.Semaphores;

import java.util.concurrent.TimeUnit;

/**
 * Created by architect on 05.03.17.
 */
public class Repeater implements Runnable {

    private static final long DEFAULT_DELAY_TIME_MS = 1000L;

    private long delayTimeInMillis;

    private boolean isRunning;

    private Runnable runnable;

    private Semaphore<Void> semaphore = Semaphores.createQueuedSemaphore();

    public Repeater(Runnable runnable) {
        this(runnable, DEFAULT_DELAY_TIME_MS);
    }

    public Repeater(Runnable runnable, long delayTimeInMillis) {
        this.runnable = ArgumentUtils.checkNotNull(runnable, "runnable");

        this.setDelayTime(delayTimeInMillis);
    }

    public Repeater(Runnable runnable, long duration, TimeUnit unit) {
        this.runnable = ArgumentUtils.checkNotNull(runnable, "runnable");

        this.setDelayTime(duration, unit);
    }

    public synchronized void setDelayTime(long delayTimeInMillis) {
        this.delayTimeInMillis = ArgumentUtils.checkGreaterZero(delayTimeInMillis, "delay time");
    }

    public synchronized void setDelayTime(long duration, TimeUnit unit) {
        ArgumentUtils.checkNotNull(unit, "unit");

        this.delayTimeInMillis = ArgumentUtils.checkGreaterZero(unit.toMillis(duration), "duration");
    }

    public long getDelayTime() {
        return this.delayTimeInMillis;
    }

    public long getDelayTime(TimeUnit other) {
        ArgumentUtils.checkNotNull(other, "other");

        return other.convert(this.delayTimeInMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public void run() {
        try {
            this.semaphore.acquire();

            if (!this.isRunning()) {
                this.isRunning = true;

                this.semaphore.release();

                while (this.isRunning()) {
                    this.runnable.run();
                }
            } else {
                this.semaphore.release();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void stop() {
        this.isRunning = false;
    }
}
