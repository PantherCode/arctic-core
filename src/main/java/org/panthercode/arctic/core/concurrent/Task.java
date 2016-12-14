package org.panthercode.arctic.core.concurrent;

import org.panthercode.arctic.core.factory.Options;
import org.panthercode.arctic.core.helper.Handler;

/**
 * TODO: documentation
 *
 * @author PantherCode
 */
public abstract class Task<T> implements Executable<T>, Runnable {
    private Options options;

    private Handler<T> handler;

    public Task() {
    }

    public Task(Options options) {
        this(options, null);
    }

    public Task(Options options, Handler<T> handler) {
        this.options = options;

        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            T result = this.execute(options);

            if (handler != null) {
                this.handler.handle(result);
            }
        } catch (Exception e) {
            this.exceptionHandler(e);
        }
    }

    public void start() {
        new Thread(this).start();
    }

    public abstract void exceptionHandler(Exception e);

    protected Options options() {
        return this.options;
    }

    protected Handler<T> handler() {
        return this.handler;
    }
}
