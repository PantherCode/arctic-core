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
