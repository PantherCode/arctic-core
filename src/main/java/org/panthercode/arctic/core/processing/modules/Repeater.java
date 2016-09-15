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
import org.panthercode.arctic.core.helper.identity.annotation.IdentityInfo;
import org.panthercode.arctic.core.helper.version.annotation.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.settings.context.Context;

import java.util.concurrent.TimeUnit;

/**
 * The Repeater repeats the module's functionality until the time limit is reached or until the module finished
 * successfully.
 */
@IdentityInfo(name = "Standard Repeater", group = "Repeater Module")
@VersionInfo(major = 1)
public class Repeater extends org.panthercode.arctic.core.processing.modules.Loop {

    /**
     * time limit
     */
    private long maximalDurationInMillis;

    /**
     * elapsed time after start the process
     */
    private long actualDurationInMillis;

    /**
     * Constructor
     *
     * @param module module for processing
     */
    public Repeater(Module module) {
        this(module, 60000, 1000, true);
    }

    /**
     * Constructor
     *
     * @param module                  module for processing
     * @param delayTimeInMillis       timeout
     * @param maximalDurationInMillis time limit
     * @param ignoreExceptions        ignore exceptions are thrown by module
     */
    public Repeater(Module module, long delayTimeInMillis, long maximalDurationInMillis, boolean ignoreExceptions) {
        this(null, module, delayTimeInMillis, maximalDurationInMillis, ignoreExceptions);
    }

    /**
     * Constructor
     *
     * @param identity                identity the object is associated with
     * @param module                  module for processing
     * @param delayTimeInMillis       timeout
     * @param maximalDurationInMillis time limit
     * @param ignoreExceptions        ignore exceptions are thrown by module
     */
    public Repeater(Identity identity, Module module, long delayTimeInMillis, long maximalDurationInMillis, boolean ignoreExceptions) {
        this(identity, module, delayTimeInMillis, maximalDurationInMillis, ignoreExceptions, null);
    }

    /**
     * Constructor
     *
     * @param identity                identity the object is associated with
     * @param module                  module for processing
     * @param delayTimeInMillis       timeout
     * @param maximalDurationInMillis time limit
     * @param ignoreExceptions        ignore exceptions are thrown by module
     * @param context                 context the object is associated with
     */
    public Repeater(Identity identity, Module module, long delayTimeInMillis, long maximalDurationInMillis, boolean ignoreExceptions, Context context) {
        super(identity, module, delayTimeInMillis, ignoreExceptions, context);

    }

    /**
     * Copy Constructor
     *
     * @param repeater object to copy
     * @throws CloneNotSupportedException Is thrown if child element doesn't support cloning.
     */
    public Repeater(Repeater repeater)
            throws CloneNotSupportedException {
        super(repeater);

        this.setMaximalDurationInMillis(repeater.getMaximalDuration());
    }

    /**
     * Returns the actual time limit (in ms).
     *
     * @return Returns the actual time limit (in ms).
     */
    public long getMaximalDuration() {
        return this.maximalDurationInMillis;
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

        return unit.convert(this.maximalDurationInMillis, this.timeUnit);
    }

    /**
     * Set a new time limit.
     *
     * @param unit     time unit
     * @param duration new time limit
     */
    public void setMaximalDuration(TimeUnit unit, long duration) {
        this.setMaximalDurationInMillis(unit.toMillis(duration));
    }

    /**
     * Set a new time limit.
     *
     * @param durationInMillis new time limit
     */
    public void setMaximalDurationInMillis(long durationInMillis) {
        ArgumentUtils.assertGreaterOrEqualsZero(durationInMillis, "duration");

        this.maximalDurationInMillis = durationInMillis;
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
     * Begin to repeat the modules start() method until the count limit is reached or (if set) the module finished
     * successfully. Before repeating the before() is called. After finishing the after() method is called.
     * <p>
     * The time limit doesn't guarantee to abort the process if time is up, but after actual run.
     */
    @Override
    public void start()
            throws Exception {
        super.start();

        this.before();

        this.actualDurationInMillis = 0L;

        for (long start = System.currentTimeMillis();
             this.actualDurationInMillis < this.maximalDurationInMillis && this.isRunning();
             this.actualDurationInMillis = System.currentTimeMillis() - start) {

            try {
                this.module.start();
            } catch (Exception e) {
                if (!this.ignoreExceptions) {
                    throw new RuntimeException("While running the module an error occurred.", e);
                }
            }

            if (module.isSucceeded()) {
                break;
            }

            this.module.reset();

            //Todo: implement TimerUtils.sleep(...)
            Thread.sleep(this.delayTimeInMillis);
        }

        this.after();

        if (this.actualDurationInMillis > this.maximalDurationInMillis) {
            if (this.canChangeState(ProcessState.FAILED)) {
                this.changeState(ProcessState.FAILED);
            }

            throw new RuntimeException("The time limit is reached. The task lasts to long.");
        }

        if (this.canChangeState(ProcessState.SUCCEEDED)) {
            this.changeState(ProcessState.SUCCEEDED);
        }
    }

    /**
     * Creates a copy of this object.
     *
     * @return Return a copy of this object.
     * @throws CloneNotSupportedException Is thrown if child element doesn't support cloning.
     */
    @Override
    public Repeater clone()
            throws CloneNotSupportedException {
        return new Repeater(this);
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
                .append(this.delayTimeInMillis)
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

        Repeater repeater = (Repeater) obj;

        return super.equals(repeater) &&
                this.maximalDurationInMillis == repeater.getMaximalDuration() &&
                this.delayTimeInMillis == repeater.getDelayTimeInMillis();
    }
}
