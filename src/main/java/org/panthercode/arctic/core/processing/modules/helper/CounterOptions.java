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
 * Builder class to configure parameters for controlling the loop process of the <tt>Counter</tt> class.
 *
 * @author PantherCode
 * @see org.panthercode.arctic.core.processing.modules.impl.Counter
 * @since 1.0
 */
public class CounterOptions extends RepeaterOptions {

    /**
     * actual maximal count of loop steps
     */
    private int count = 1;

    /**
     * Constructor
     */
    public CounterOptions() {
        super();
    }

    /**
     * Constructor
     *
     * @param count maximal count of loop steps
     */
    public CounterOptions(int count) {
        this(1, 1000L);
    }

    /**
     * Constructor
     *
     * @param count             maximal count of loop steps
     * @param delayTimeInMillis delay time after each loop step
     */
    public CounterOptions(int count, long delayTimeInMillis) {
        this(1, delayTimeInMillis, true, true);
    }

    /**
     * Constructor
     *
     * @param count             maximal count of loop steps
     * @param delayTimeInMillis delay time after each loop step
     * @param ignoreExceptions  flag to ignore occurred exceptions
     * @param canQuit           flag to quit process
     */
    public CounterOptions(int count, long delayTimeInMillis, boolean ignoreExceptions, boolean canQuit) {
        super(delayTimeInMillis, ignoreExceptions, canQuit);

        this.setCount(count);
    }

    /**
     * Returns the actual value of the maximal count of loop steps.
     *
     * @return Returns the actual value of the maximal count of loop steps.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Sets the maximal count of loop steps.
     *
     * @param count maximal count of loop steps
     */
    public void setCount(int count) {
        ArgumentUtils.checkGreaterOrEqualsZero(count, "count");

        this.count = count;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString() + " count = " + this.count;
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
     * @return Returns <tt>true</tt> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof CounterOptions)) {
            return false;
        }

        CounterOptions options = (CounterOptions) obj;

        return super.equals(obj) &&
                this.getCount() == options.getCount();
    }
}
