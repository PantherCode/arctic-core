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
package org.panthercode.arctic.core.processing.modules.options;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.arguments.ArgumentUtils;

/**
 * TODO: documentation
 * TODO: Exceptions in function signature
 */
public class CounterOptions extends LoopOptions {

    /**
     *
     */
    private int count = 1;

    /**
     *
     */
    public CounterOptions() {
        super();
    }

    /**
     * @param count
     */
    public CounterOptions(int count) {
        this(1, 1000L);
    }

    /**
     * @param count
     * @param delayTimeInMillis
     */
    public CounterOptions(int count, long delayTimeInMillis) {
        this(1, delayTimeInMillis, true, true);
    }

    /**
     * @param count
     * @param delayTimeInMillis
     * @param ignoreExceptions
     * @param canQuit
     */
    public CounterOptions(int count, long delayTimeInMillis, boolean ignoreExceptions, boolean canQuit) {
        super(delayTimeInMillis, ignoreExceptions, canQuit);

        this.setCount(count);
    }

    /**
     * @return
     */
    public int getCount() {
        return this.count;
    }

    /**
     * @param count
     */
    public void setCount(int count) {
        ArgumentUtils.assertGreaterOrEqualsZero(count, "count");

        this.count = count;
    }

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
     * @return Returns <code>true</code> if both objects are equal; Otherwise <tt>false</tt>.
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
