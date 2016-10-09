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
public class LoopOptions {

    /**
     *
     */
    private long delayTimeInMillis = 1000L;

    /**
     *
     */
    private boolean ignoreExceptions = true;

    /**
     *
     */
    private boolean canQuit = true;

    /**
     *
     */
    public LoopOptions() {
    }

    /**
     * @param delayTimeInMillis
     */
    public LoopOptions(long delayTimeInMillis) {
        this(delayTimeInMillis, true, true);
    }

    /**
     * @param delayTimeInMillis
     * @param ignoreExceptions
     * @param canQuit
     */
    public LoopOptions(long delayTimeInMillis, boolean ignoreExceptions, boolean canQuit) {
        this.setDelayTime(delayTimeInMillis);
        this.ignoreExceptions(ignoreExceptions);
        this.canQuit(canQuit);
    }

    /**
     * @return
     */
    public long getDelayTime() {
        return this.delayTimeInMillis;
    }

    /**
     * @param delayTimeInMillis
     */
    public void setDelayTime(long delayTimeInMillis) {
        ArgumentUtils.assertGreaterOrEqualsZero(delayTimeInMillis, "delay time");

        this.delayTimeInMillis = delayTimeInMillis;
    }

    /**
     * @return
     */
    public boolean isIgnoreExceptions() {
        return this.ignoreExceptions;
    }

    /**
     * @param ignoreExceptions
     */
    public void ignoreExceptions(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    /**
     * @return
     */
    public boolean canQuit() {
        return this.canQuit;
    }

    /**
     * @param canQuit
     */
    public void canQuit(boolean canQuit) {
        this.canQuit = canQuit;
    }


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
     * @return Returns <code>true</code> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof LoopOptions)) {
            return false;
        }

        LoopOptions options = (LoopOptions) obj;

        return options.canQuit() == this.canQuit() &&
                options.isIgnoreExceptions() == this.isIgnoreExceptions() &&
                options.getDelayTime() == this.getDelayTime();
    }
}
