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
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.processing.exceptions.ProcessException;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.processing.modules.options.LoopOptions;
import org.panthercode.arctic.core.settings.context.Context;

import java.util.concurrent.TimeUnit;

//TODO: update documentation

/**
 * Class to repeat module's functionality. The Loop class provides some basic functions to control the loop process.
 */
public abstract class Loop extends ModuleImpl {

    /**
     * Module for processing
     */
    protected Module module = null;

    /**
     *
     */
    protected LoopOptions options;

    /**
     * Loop class use milliseconds for measurements
     */
    protected final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException Is thrown if value of module or identity is null.
     */
    public Loop(Module module)
            throws NullPointerException {
        this(module, new LoopOptions());
    }

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException     Is thrown if value of module or identity is null.
     * @throws IllegalArgumentException
     */
    public Loop(Module module,
                LoopOptions options)
            throws NullPointerException, IllegalArgumentException {
        this(module, options, null);
    }

    /**
     * Constructor
     *
     * @param module  module for processing
     * @param context context the object is associated with
     * @throws NullPointerException Is thrown if value of module or identity is null.
     *                              throws IllegalArgumentException
     */
    public Loop(Module module,
                LoopOptions options,
                Context context)
            throws NullPointerException, IllegalArgumentException {
        super(context);

        ArgumentUtils.assertNotNull(options, "options");

        this.setModule(module);

        this.options = options;
    }

    /**
     * Copy Constructor
     *
     * @param loop object to copy
     * @throws NullPointerException          Is thrown if value of parameter is null.
     * @throws UnsupportedOperationException Is thrown if child element doesn't support cloning.
     */
    public Loop(Loop loop)
            throws NullPointerException, UnsupportedOperationException {
        super(loop);

        this.options = new LoopOptions(loop.getDelayTime(), loop.ignoreExceptions(), loop.canQuit());

        this.setModule(loop.getModule().copy());
    }

    /**
     * Set new context the object is associated with. It also sets the context of child element. You can only set a new
     * context to this object if process state isn't "Running" or "Waiting".
     *
     * @param context new context
     * @return
     */
    @Override
    public synchronized boolean setContext(final Context context) {
        return super.setContext(context) && this.module.setContext(context);
    }

    /**
     * Set a module the object is associated with. You can only set a new module, if objects process state is "Ready".
     *
     * @param module new module for processing
     * @return
     * @throws NullPointerException Is thrown if module parameter has value <tt>null</tt>
     */
    public synchronized boolean setModule(final Module module)
            throws NullPointerException, IllegalStateException {
        if (this.isReady()) {
            ArgumentUtils.assertNotNull(module, "module");

            this.module = module;

            this.module.setContext(this.context);

            return true;
        }

        return false;
    }

    /**
     * Returns the module the object is associated with.
     *
     * @return Returns the module the object is associated with.
     */
    public Module getModule() {
        return this.module;
    }

    /**
     * Set a new delay time the object is associated with. The value must be greater than or equals zero.
     *
     * @param durationInMillis new timeout
     * @throws IllegalArgumentException Is thrown if value of parameter is less than zero.
     */
    public void setDelayTime(long durationInMillis)
            throws IllegalArgumentException {
        ArgumentUtils.assertGreaterOrEqualsZero(durationInMillis, "duration");

        this.options.setDelayTime(durationInMillis);
    }

    /**
     * Set a new delay time the object is associated with. The value must be greater than or equals zero.
     *
     * @param unit     time unit of duration
     * @param duration new timeout value
     * @throws IllegalArgumentException Is thrown if value of duration is less than zero.
     * @throws NullPointerException
     */
    public void setDelayTime(TimeUnit unit, long duration)
            throws NullPointerException, IllegalArgumentException {
        ArgumentUtils.assertNotNull(unit, "time unit");

        this.setDelayTime(unit.toMillis(duration));
    }

    /**
     * Returns the actual delay time the object is associated with.
     *
     * @return Returns the actual delay time the object is associated with.
     */
    public long getDelayTime() {
        return this.options.getDelayTime();
    }

    /**
     * Returns the actual delay time the object is associated with.
     *
     * @param unit own time unit
     * @return Returns the actual delay time the object is associated with.
     * @throws NullPointerException
     */
    public long getDelayTime(final TimeUnit unit) {
        ArgumentUtils.assertNotNull(timeUnit, "timeUnit");

        return unit.convert(this.options.getDelayTime(), this.timeUnit);
    }

    /**
     * Returns a flag representing whether the object ignores exceptions are thrown by module or not.
     *
     * @return Returns <tt>true</tt> if object ignores exceptions; Otherwise <tt>false</tt>.
     */
    public boolean ignoreExceptions() {
        return this.options.isIgnoreExceptions();
    }

    /**
     * Set a flag to ignore exceptions thrown by value or not.
     *
     * @param ignoreExceptions flag tp ignore exceptions
     */
    public void ignoreExceptions(boolean ignoreExceptions) {
        this.options.ignoreExceptions(ignoreExceptions);
    }

    /**
     * @return
     */
    public boolean canQuit() {
        return this.options.canQuit();
    }

    /**
     * @param canQuit
     */
    public void canQuit(boolean canQuit) {
        this.options.canQuit(canQuit);
    }

    /**
     * This method will be called before loop process starts.
     */
    public void before() throws ProcessException {
    }

    /**
     * This method will be called after loop process finished.
     */
    public void after() throws ProcessException {
    }

    /**
     * @return
     * @throws ProcessException
     */
    @Override
    public synchronized boolean stop() throws ProcessException {
        return super.stop() && this.module.stop();
    }

    /**
     * @return
     * @throws ProcessException
     */
    @Override
    public synchronized boolean reset() throws ProcessException {
        return super.reset() && this.module.reset();
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
                .append(this.module.hashCode())
                .append(this.getDelayTime())
                .append(this.ignoreExceptions())
                .append(this.canQuit())
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

        if (!(obj instanceof Loop)) {
            return false;
        }

        Loop loop = (Loop) obj;
        return super.equals(loop) &&
                this.module.equals(loop.getModule()) &&
                this.getDelayTime() == loop.getDelayTime() &&
                this.ignoreExceptions() == loop.ignoreExceptions() &&
                this.canQuit() == loop.canQuit();
    }
}
