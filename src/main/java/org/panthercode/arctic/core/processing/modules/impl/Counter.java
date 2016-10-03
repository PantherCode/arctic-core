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
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.annotation.IdentityInfo;
import org.panthercode.arctic.core.helper.version.annotation.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.exceptions.ProcessException;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.settings.context.Context;

//TODO: create Builder class
//TODO: update documentation

/**
 * The Counter class repeats the module's functionality until a numeric limit is reached or (if canAbort flag is set)
 * until the module finished successfully.
 */
@IdentityInfo(name = "Standard Counter", group = "Counter Module")
@VersionInfo(major = 1)
public class Counter extends Loop {

    /**
     * maximal count of repeats
     */
    private int count = 1;

    /**
     * actual round
     */
    private int actualCount = 0;

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException
     */
    public Counter(Module module)
            throws NullPointerException {
        this(module, 1, 1000, true, true);
    }

    /**
     * Constructor
     *
     * @param module            module to processing
     * @param count             maximal counts of repeats
     * @param delayTimeInMillis timeout after each round
     * @param canQuit           flag to abort the process before count is reached
     * @param ignoreExceptions  ignore exceptions are thrown by module
     * @throws NullPointerException
     */
    public Counter(Module module,
                   int count,
                   long delayTimeInMillis,
                   boolean canQuit,
                   boolean ignoreExceptions)
            throws NullPointerException {
        this(module, count, delayTimeInMillis, canQuit, ignoreExceptions, null);
    }

    /**
     * Constructor
     *
     * @param module
     * @param count
     * @param delayTimeInMillis
     * @param canQuit
     * @param ignoreExceptions
     * @param context
     * @throws NullPointerException
     */
    public Counter(Module module,
                   int count,
                   long delayTimeInMillis,
                   boolean canQuit,
                   boolean ignoreExceptions,
                   Context context)
            throws NullPointerException {
        this(null, module, count, delayTimeInMillis, canQuit, ignoreExceptions, context);
    }


    /**
     * Constructor
     *
     * @param identity          identity the object is associated with
     * @param module            module to processing
     * @param count             maximal counts of repeats
     * @param delayTimeInMillis timeout after each round
     * @param canQuit           flag to abort the process before count is reached
     * @param ignoreExceptions  ignore exceptions are thrown by module
     * @param context           context the object is associated with
     */
    public Counter(Identity identity,
                   Module module,
                   int count,
                   long delayTimeInMillis,
                   boolean canQuit,
                   boolean ignoreExceptions,
                   Context context)
            throws NullPointerException {
        super(identity, module, delayTimeInMillis, ignoreExceptions, canQuit, context);

        this.setCount(count);
    }

    /**
     * Copy Constructor
     *
     * @param counter object to copy
     * @throws CloneNotSupportedException Is thrown if child element doesn't support cloning.
     */
    public Counter(Counter counter)
            throws CloneNotSupportedException {
        super(counter);

        this.setCount(counter.getCount());
    }

    /**
     * Return the actual maximal number of repeats.
     *
     * @return Return the actual maximal number of repeats.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Set a new number as maximum for repeats.
     *
     * @param count new maximal number of repeats
     * @throws IllegalArgumentException Is thrown if value is zero or less.
     */
    public synchronized void setCount(final int count) {
        ArgumentUtils.assertGreaterZero(count, "count");

        this.count = count;
    }

    /**
     * Returns the number repeats have done until starting the process.
     *
     * @return Returns the actual number of repeats have done or zero if process state is not "Running".
     */
    public int actualCount() {
        return (this.isRunning()) ? this.actualCount : 0;
    }

    /**
     * Begin to repeat the modules start() method until the count limit is reached or (if set) the module finished
     * successfully. Before repeating the before() is called. After finishing the after() method is called.
     *
     * @throws ProcessException Is thrown if an exceptions is thrown by module and flag <tt>ignoreExceptions</tt> is
     *                   <tt>false</tt>.
     */
    @Override
    public synchronized boolean start()
            throws ProcessException {
        if(super.start()) {
            before();

            this.actualCount = 0;
            int loop;

            for (loop = 0; loop <= this.count && this.isRunning(); loop++, this.actualCount = loop) {
                this.module.reset();

                try {
                    this.module.start();

                    if ((module.isSucceeded() && this.canQuit) || this.module.isStopped()) {
                        break;
                    }
                } catch (ProcessException e) {
                    if (!this.ignoreExceptions) {
                        this.changeState(ProcessState.FAILED);
                        throw new ProcessException("While running the module an error occurred.", e);
                    }
                }

                try {
                    Thread.sleep(this.getDelayTimeInMillis());
                } catch (InterruptedException e) {
                    throw new ProcessException(e);
                }
            }

            if(!this.isStopped()) {
                ProcessState result = (!this.canQuit || this.module.isSucceeded()) ? ProcessState.SUCCEEDED
                        : ProcessState.FAILED;

                if (!this.changeState(result)) {
                    throw new ProcessException("Failed to set status to " + result + ".");
                }
            }

            after();

            return !isStopped();
        }

        return false;
    }

    /**
     * Creates a copy of this object.
     *
     * @return Return a copy of this object.
     * @throws CloneNotSupportedException Is thrown if child element doesn't support cloning.
     */
    @Override
    public ModuleImpl clone()
            throws CloneNotSupportedException {
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
                .append(this.count)
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

        Counter counter = (Counter) obj;
        return super.equals(counter) &&
                this.canQuit == counter.canQuit() &&
                this.count == counter.getCount();
    }
}

