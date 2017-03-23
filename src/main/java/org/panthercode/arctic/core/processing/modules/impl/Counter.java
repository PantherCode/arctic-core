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
package org.panthercode.arctic.core.processing.modules.impl;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.processing.modules.helper.Controller;
import org.panthercode.arctic.core.processing.modules.helper.CounterOptions;
import org.panthercode.arctic.core.settings.Context;

//TODO: update documentation

/**
 * The Counter class repeats the module's functionality until a numeric limit is reached or (if canAbort flag is set)
 * until the module finished successfully.
 *
 * @author PantherCode
 * @see Module
 * @since 1.0
 */
@IdentityInfo(name = "Standard Counter", group = "Counter Module")
@VersionInfo(major = 1)
public class Counter extends Repeater {

    private CounterController controller = null;

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException
     */
    public Counter(Module module)
            throws NullPointerException {
        this(module, new CounterOptions());
    }

    /**
     * Constructor
     *
     * @param module module to processing
     * @throws NullPointerException
     */
    public Counter(Module module,
                   CounterOptions options)
            throws NullPointerException {
        this(module, options, null);
    }

    /**
     * Constructor
     *
     * @param module
     * @param context
     * @throws NullPointerException
     */
    public Counter(Module module,
                   CounterOptions options,
                   Context context)
            throws NullPointerException {
        super(module, options, context);
    }

    /**
     * Copy Constructor
     *
     * @param counter object to copy
     */
    public Counter(Counter counter) {
        super(counter.getModule().copy(),
                new CounterOptions(counter.getCount(),
                        counter.getDelayTime(),
                        counter.isIgnoreExceptions(),
                        counter.canQuit()),
                counter.getContext());
    }


    /**
     * Return the actual maximal number of repeats.
     *
     * @return Return the actual maximal number of repeats.
     */
    public int getCount() {
        return ((CounterOptions) this.options).getCount();
    }

    /**
     * Set a new number as maximum for repeats.
     *
     * @param count new maximal number of repeats
     * @throws IllegalArgumentException Is thrown if value is zero or less.
     */
    public synchronized void setCount(final int count) {
        ((CounterOptions) this.options).setCount(count);
    }

    /**
     * Returns the number repeats have done until starting the process.
     *
     * @return Returns the actual number of repeats have done or zero if process state is not "Running".
     */
    public int actualCount() {
        return (this.isRunning()) ? this.controller.actualCount : 0;
    }


    @Override
    public boolean hasResult() {
        return false;
    }

    @Override
    public Object result() {
        return null;
    }

    @Override
    public Counter copy()
            throws UnsupportedOperationException {
        return new Counter(this);
    }

    /**
     * Returns a hash code value of this object.
     *
     * @return Returns a hash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(super.hashCode())
                .append(this.getCount())
                .toHashCode());
    }

    /**
     * Checks if this object is equals to another one.
     *
     * @param obj other object for comparison
     * @return Returns <code>true</code> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Repeater)) {
            return false;
        }

        Counter counter = (Counter) obj;
        return super.equals(counter) &&
                this.canQuit() == counter.canQuit() &&
                this.getCount() == counter.getCount();
    }

    @Override
    protected Controller<? extends Object> createController() {
        this.controller = new CounterController();

        return this.controller;
    }

    private class CounterController extends Controller<Integer> {

        private int actualCount = 0;

        @Override
        public void reset() {
            this.actualCount = 0;
        }

        @Override
        public Integer value() {
            return this.actualCount;
        }

        @Override
        public void update() {
            this.actualCount++;
        }

        @Override
        public boolean accept() {
            return this.actualCount < getCount();
        }
    }
}

