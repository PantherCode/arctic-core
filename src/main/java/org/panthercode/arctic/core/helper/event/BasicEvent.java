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
package org.panthercode.arctic.core.helper.event;

/**
 * Abstract class of an event.
 *
 * @author PantherCode
 * @see Event
 * @since 1.0
 */
public abstract class BasicEvent {
    /**
     * flag whether event is handled or not
     */
    private boolean isHandled = false;

    /**
     * Standard Constructor
     */
    public BasicEvent() {
    }

    /**
     * Sets the handle flag of event.
     *
     * @param handled value of flag
     */
    public void isHandled(boolean handled) {
        this.isHandled = handled;
    }

    /**
     * Returns a flag that indicates whether the event is handled or not.
     *
     * @return Returns <tt>true</tt> if event is handled; Otherwise <tt>false</tt>
     */
    public boolean isHandled() {
        return isHandled;
    }
}
