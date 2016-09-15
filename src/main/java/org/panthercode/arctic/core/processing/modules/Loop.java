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
package org.panthercode.arctic.core.processing.modules;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.settings.context.Context;

import java.util.concurrent.TimeUnit;

/**
 * Class to repeat module's functionality. The Loop class provides some basic functions to control the loop process.
 */
public abstract class Loop extends org.panthercode.arctic.core.processing.modules.AbstractModule {

    /**
     * Module for processing
     */
    protected Module module = null;

    /**
     * Timeout after each round
     */
    protected long delayTimeInMillis = 1000L;

    /**
     * Flag to ignore exception are thrown by module
     */
    protected boolean ignoreExceptions = true;

    /**
     * Loop class use milliseconds for measurements
     */
    protected final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * Constructor
     *
     * @param module module for processing
     */
    public Loop(Module module) {
        this(module, 1000L, true);
    }

    /**
     * Constructor
     *
     * @param module            module for processing
     * @param delayTimeInMillis timeout after each round
     * @param ignoreExceptions  ignore exceptions are thrown by module
     */
    public Loop(Module module, long delayTimeInMillis, boolean ignoreExceptions) {
        this(null, module, delayTimeInMillis, ignoreExceptions);
    }

    /**
     * Constructor
     *
     * @param identity          identity the object is associated with
     * @param module            module for processing
     * @param delayTimeInMillis timeout after each round
     * @param ignoreExceptions  ignore exceptions are thrown by module
     */
    public Loop(Identity identity, Module module, long delayTimeInMillis, boolean ignoreExceptions) {
        this(identity, module, delayTimeInMillis, ignoreExceptions, null);
    }

    /**
     * Constructor
     *
     * @param identity          identity the object is associated with
     * @param module            module for processing
     * @param delayTimeInMillis timeout after each round
     * @param ignoreExceptions  ignore exceptions are thrown by module
     * @param context           context the object is associated with
     */
    public Loop(Identity identity, Module module, long delayTimeInMillis, boolean ignoreExceptions, Context context) {
        super(identity, context);

        this.setContext(context);

        this.setModule(module);

        this.setDelayTimeInMillis(delayTimeInMillis);
    }

    /**
     * Copy Constructor
     *
     * @param loop object to copy
     */
    public Loop(Loop loop)
            throws CloneNotSupportedException {
        super(loop);

        this.setModule(loop.getModule().clone());

        this.setDelayTimeInMillis(loop.getDelayTimeInMillis());
    }

    /**
     * Set new context the object is associated with. It also sets the context of child element. You can only set a new
     * context to this object if process state isn't "Running" or "Waiting".
     *
     * @param context new context
     */
    @Override
    public synchronized void setContext(final Context context) {
        if (!this.isRunning() && !this.isWaiting()) {
            super.setContext(context);

            this.module.setContext(context);
        }
    }

    /**
     * Set a module the object is associated with. You can only set a new module, if objects process state is "Ready".
     *
     * @param module new module for processing
     * @throws RuntimeException Is thrown if module parameter has value <tt>null</tt> or object's process state is not
     *                          "Ready".
     */
    public void setModule(final Module module)
            throws RuntimeException {
        if (this.isReady()) {
            ArgumentUtils.assertNotNull(module, "module");

            this.module = module;
        } else {
            throw new RuntimeException("You can only set a new module, if object is in state " + ProcessState.READY +
                    ".");
        }
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
    public void setDelayTimeInMillis(long durationInMillis)
            throws IllegalArgumentException {
        this.setDelayTimeInMillis(TimeUnit.MILLISECONDS, durationInMillis);
    }

    /**
     * Set a new delay time the object is associated with. The value must be greater than or equals zero.
     *
     * @param unit     time unit of duration
     * @param duration new timeout value
     * @throws IllegalArgumentException Is thrown if value of duration is less than zero.
     */
    public void setDelayTimeInMillis(TimeUnit unit, long duration)
            throws IllegalArgumentException {
        ArgumentUtils.assertNotNull(unit, "time unit");
        ArgumentUtils.assertGreaterOrEqualsZero(duration, "duration");

        this.setDelayTimeInMillis(unit.toMillis(duration));
    }

    /**
     * Returns the actual delay time the object is associated with.
     *
     * @return Returns the actual delay time the object is associated with.
     */
    public long getDelayTimeInMillis() {
        return this.delayTimeInMillis;
    }

    /**
     * Returns the actual delay time the object is associated with.
     *
     * @param unit own time unit
     * @return Returns the actual delay time the object is associated with.
     */
    public long getDelayTimeInMillis(final TimeUnit unit) {
        ArgumentUtils.assertNotNull(timeUnit, "timeUnit");

        return unit.convert(this.delayTimeInMillis, this.timeUnit);
    }

    /**
     * Returns a flag representing whether the object ignores exceptions are thrown by module or not.
     *
     * @return Returns <tt>true</tt> if object ignores exceptions; Otherwise <tt>false</tt>.
     */
    public boolean isIgnoreExceptions() {
        return this.ignoreExceptions;
    }

    /**
     * Set a flag to ignore exceptions thrown by value or not.
     *
     * @param ignoreExceptions flag tp ignore exceptions
     */
    public void ignoreExceptions(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    /**
     * This method will be called before loop process starts.
     */
    public void before() {
    }

    /**
     * This method will be called after loop process finished.
     */
    public void after() {
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
                .append(this.delayTimeInMillis)
                .append(this.ignoreExceptions)
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
                this.delayTimeInMillis == loop.getDelayTimeInMillis() &&
                this.ignoreExceptions == loop.isIgnoreExceptions();
    }
}
