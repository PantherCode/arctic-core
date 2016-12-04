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
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.processing.modules.helper.RepeaterOptions;
import org.panthercode.arctic.core.settings.Context;

import java.util.concurrent.TimeUnit;

//TODO: udpate documentation

/**
 * The Repeater repeats the module's functionality until the time limit is reached or until the module finished
 * successfully.
 */
@IdentityInfo(name = "Standard Repeater", group = "Repeater Module")
@VersionInfo(major = 1)
public class Timer extends Repeater {

    /**
     * elapsed time after start the process
     */
    private long actualDurationInMillis;

    private long startPoint;

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException
     */
    public Timer(Module module)
            throws NullPointerException {
        this(module, new RepeaterOptions());
    }

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException
     */
    public Timer(Module module,
                 RepeaterOptions options)
            throws NullPointerException {
        this(module, options, null);
    }

    /**
     * Constructor
     *
     * @param module  module for processing
     * @param context context the object is associated with
     * @throws NullPointerException
     */
    public Timer(Module module,
                 RepeaterOptions options,
                 Context context)
            throws NullPointerException {
        super(module, options, context);
    }

    /**
     * Copy Constructor. Hint: Use only Copy Constructor construct LoopOption object, if you call getMaximalDuration()
     * a ClassCastException will be thrown. Therefore it's used a "normal" constructor.
     *
     * @param timer object to copy
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public Timer(Timer timer)
            throws NullPointerException, UnsupportedOperationException {
        super(timer.getModule().copy(),
                new RepeaterOptions(timer.getMaximalDuration(),
                        timer.getDelayTime(),
                        timer.isIgnoreExceptions(),
                        timer.canQuit()),
                timer.getContext());
    }

    /**
     * Returns the actual time limit (in ms).
     *
     * @return Returns the actual time limit (in ms).
     */
    public long getMaximalDuration() {
        return ((RepeaterOptions) this.options).getMaximalDuration();
    }

    /**
     * Returns the actual time limit.
     *
     * @param unit alternative time unit
     * @return Returns the actual time limit.
     * @throws NullPointerException Is thrown if the value of unit is null.
     */
    public long getMaximalDuration(TimeUnit unit) {
        ArgumentUtils.assertNotNull(unit, "time unit");

        return unit.convert(this.getMaximalDuration(), this.timeUnit);
    }

    /**
     * Set a new time limit.
     *
     * @param unit     time unit
     * @param duration new time limit
     * @throws IllegalArgumentException
     */
    public void setMaximalDuration(TimeUnit unit, long duration)
            throws IllegalArgumentException {
        this.setMaximalDuration(unit.toMillis(duration));
    }

    /**
     * Set a new time limit.
     *
     * @param durationInMillis new time limit
     * @throws IllegalArgumentException
     */
    public synchronized void setMaximalDuration(long durationInMillis) {
        ((RepeaterOptions) this.options).setMaximalDuration(durationInMillis);
    }

    /**
     * Returns the elapsed time after starting the process.
     *
     * @return Returns the elapsed time if object's process state is "Running"; Otherwise zero.
     */
    public long actualDuration() {
        return this.isRunning() ? 0L : this.actualDurationInMillis;
    }

    /**
     * Creates a copy of this object.
     *
     * @return Return a copy of this object.
     * @throws CloneNotSupportedException Is thrown if child element doesn't support cloning.
     */
    @Override
    public Timer copy()
            throws UnsupportedOperationException {
        return new Timer(this);
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
                .append(this.actualDurationInMillis)
                .append(this.getDelayTime())
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

        if (!(obj instanceof Timer)) {
            return false;
        }

        Timer timer = (Timer) obj;

        return super.equals(timer) &&
                this.getMaximalDuration() == timer.getMaximalDuration() &&
                this.getDelayTime() == timer.getDelayTime();
    }

    //@Override
    protected void initialiseLoop() {
        this.actualDurationInMillis = 0L;
        this.startPoint = System.currentTimeMillis();
    }

    //@Override
    protected boolean loopCondition() {
        return this.actualDuration() < this.getMaximalDuration();
    }

    //@Override
    protected void afterLoop() {
        this.actualDurationInMillis = System.currentTimeMillis() - this.startPoint;
    }
}
