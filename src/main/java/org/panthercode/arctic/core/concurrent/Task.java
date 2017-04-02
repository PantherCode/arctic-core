/*
 * Copyright 2016 PantherCode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.panthercode.arctic.core.concurrent;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.event.Handler;
import org.panthercode.arctic.core.settings.Context;

/**
 * Tasks are special runnable object with the possibility to run such objects in special context and handle results
 * asynchronously by initializing a responseHandler. In combination with <tt>WorkerOld</tt> class you can create very simple and
 * fast a powerful processing unit.
 * <p>
 * To run the task in a new  thread, you can call <tt>start()</tt> method. If only the task should run synchronously in
 * same thread call <tt>run()</tt>.
 *
 * @author PantherCode
 * @since 1.0
 */
public abstract class Task<T> implements Executable<T>, Runnable {

    /**
     * actual context of the object
     */
    private Context context;

    /**
     * object to handle the result of operation
     */
    private Handler<T> handler;

    /**
     * Standard Constructor
     */
    public Task() {
        this(new Context(), null);
    }

    /**
     * Constructor
     *
     * @param context actual context of the object
     */
    public Task(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context actual context of the object
     * @param handler object to handle the result
     */
    public Task(Context context, Handler<T> handler) {
        this.context = ArgumentUtils.checkNotNull(context, "context");

        this.handler = ArgumentUtils.checkNotNull(handler, "responseHandler");
    }

    /**
     * Execute the task and call responseHandler if anyone exists.
     */
    @Override
    public void run() {
        try {
            T result = this.execute(this.context);

            if (this.handler != null) {
                this.handler.handle(result);
            }
        } catch (Exception e) {
            this.exceptionHandler(e);
        }
    }

    /**
     * Object to handle occurred exception while running task.
     *
     * @param e exception object
     */
    public abstract void exceptionHandler(Exception e);

    /**
     * Returns the actual context object.
     *
     * @return Returns the actual context object.
     */
    protected Context context() {
        return this.context;
    }

    /**
     * Returns the actual result responseHandler.
     *
     * @return Returns the actual result responseHandler.
     */
    protected Handler<T> handler() {
        return this.handler;
    }
}
