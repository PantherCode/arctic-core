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

/**
 * Helper class to control the repeat process of <tt>Repeater</tt> class and there children.
 *
 * @author PantherCode
 */
public abstract class Controller<T> {

    /**
     * Set the actual inner state to an initial value.
     */
    public abstract void reset();

    /**
     * Returns the actual value of inner state.
     *
     * @return Returns the actual value of inner state.
     */
    public abstract T value();

    /**
     * Refresh the value of inner state.
     */
    public abstract void update();

    /**
     * Returns a flag whether the actul inner state is valid or not.
     *
     * @return Returns <tt>true</tt> if condition is accepted; Otherwise <tt>false</tt>.
     */
    public abstract boolean accept();
}
