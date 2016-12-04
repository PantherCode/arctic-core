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
 * TODO: documentation
 * TODO: Exceptions in function signature
 */
public class TimerOptions extends RepeaterOptions {

    /**
     *
     */
    private long maximalDurationInMillis = 60000L;

    /**
     *
     */
    public TimerOptions() {
        super();
    }

    /**
     * @param maximalDurationInMillis
     */
    public TimerOptions(long maximalDurationInMillis) {
        this(maximalDurationInMillis, 1000L);
    }

    /**
     * @param maximalDurationInMillis
     * @param delayTimeInMillis
     */
    public TimerOptions(long maximalDurationInMillis, long delayTimeInMillis) {
        this(maximalDurationInMillis, delayTimeInMillis, true, true);
    }

    /**
     * @param maximalDurationInMillis
     * @param delayTimeInMillis
     * @param ignoreExceptions
     * @param canQuit
     */
    public TimerOptions(long maximalDurationInMillis,
                        long delayTimeInMillis,
                        boolean ignoreExceptions,
                        boolean canQuit) {
        super(delayTimeInMillis, ignoreExceptions, canQuit);

        this.setMaximalDuration(maximalDurationInMillis);
    }

    /**
     * @return
     */
    public long getMaximalDuration() {
        return this.maximalDurationInMillis;
    }

    /**
     * @param maximalDurationInMillis
     */
    public void setMaximalDuration(long maximalDurationInMillis) {
        ArgumentUtils.assertGreaterOrEqualsZero(maximalDurationInMillis, "maximal duration");

        this.maximalDurationInMillis = maximalDurationInMillis;
    }

    @Override
    public String toString() {
        return super.toString() + " maximal duration = " + this.maximalDurationInMillis + " ms";
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
                .append(this.maximalDurationInMillis)
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

        if (!(obj instanceof TimerOptions)) {
            return false;
        }

        TimerOptions options = (TimerOptions) obj;

        return super.equals(obj) &&
                this.getMaximalDuration() == options.getMaximalDuration();
    }
}
