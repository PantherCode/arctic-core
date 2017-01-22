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
package org.panthercode.arctic.core.processing.modules.helper;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.arguments.ArgumentUtils;

/**
 * Builder class to configure parameters for controlling the loop process of the <tt>Repeater</tt> class.
 *
 * @author PantherCode
 * @see org.panthercode.arctic.core.processing.modules.impl.Repeater
 * @since Repeater
 */
public class RepeaterOptions {

    /**
     * actual delay time after each loop step
     */
    private long delayTimeInMillis = 1000L;

    /**
     * flag whether occurred exceptions are ignored or not.
     */
    private boolean ignoreExceptions = true;

    /**
     * flag whether the process can quit after first successful loop step or not
     */
    private boolean canQuit = true;

    /**
     * Default Constructor
     */
    public RepeaterOptions() {
    }

    /**
     * Constructor
     *
     * @param delayTimeInMillis delay time after each loop step
     */
    public RepeaterOptions(long delayTimeInMillis) {
        this(delayTimeInMillis, true, true);
    }

    /**
     * Constructor
     *
     * @param delayTimeInMillis delay time after each loop step
     * @param ignoreExceptions  flag to ignore occurred exceptions
     * @param canQuit           flag to quit process
     */
    public RepeaterOptions(long delayTimeInMillis, boolean ignoreExceptions, boolean canQuit) {
        this.setDelayTime(delayTimeInMillis);
        this.ignoreExceptions(ignoreExceptions);
        this.canQuit(canQuit);
    }

    /**
     * Returns the actual delay time.
     *
     * @return Returns the actual time.
     */
    public long getDelayTime() {
        return this.delayTimeInMillis;
    }

    /**
     * Sets the actual delay time.
     *
     * @param delayTimeInMillis delay time after each loop step
     */
    public void setDelayTime(long delayTimeInMillis) {
        ArgumentUtils.assertGreaterOrEqualsZero(delayTimeInMillis, "delay time");

        this.delayTimeInMillis = delayTimeInMillis;
    }

    /**
     * Returns a flag that indicates whether occurred exceptions are ignored or not.
     *
     * @return Returns <tt>true</tt> if flag is set; Otherwise <tt>false</tt>.
     */
    public boolean isIgnoreExceptions() {
        return this.ignoreExceptions;
    }

    /**
     * Sets a flag that indicates whether occurred exceptions are ignored or not.
     *
     * @param ignoreExceptions value of flag
     */
    public void ignoreExceptions(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    /**
     * Returns a flag that indicates whether the process can quit after first successful loop step or not.
     *
     * @return Returns <tt>true</tt> if flag is set; Otherwise <tt>false</tt>.
     */
    public boolean canQuit() {
        return this.canQuit;
    }

    /**
     * Sets a flag that indicates whether the process can quit after first successfil loop step or not
     *
     * @param canQuit value of flag
     */
    public void canQuit(boolean canQuit) {
        this.canQuit = canQuit;
    }


    /**
     * Returns a string representation of the object.
     *
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "delay time = " + this.delayTimeInMillis
                + " ms, can quit = " + this.canQuit
                + " ignore exception " + this.ignoreExceptions;
    }

    /**
     * Returns a hash code value of this object.
     *
     * @return Returns a hash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(this.delayTimeInMillis)
                .append(this.ignoreExceptions)
                .append(this.canQuit)
                .toHashCode());
    }

    /**
     * Checks if this object is equals to another one.
     *
     * @param obj other object for comparison
     * @return Returns <tt>true</tt> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof RepeaterOptions)) {
            return false;
        }

        RepeaterOptions options = (RepeaterOptions) obj;

        return options.canQuit() == this.canQuit() &&
                options.isIgnoreExceptions() == this.isIgnoreExceptions() &&
                options.getDelayTime() == this.getDelayTime();
    }
}
