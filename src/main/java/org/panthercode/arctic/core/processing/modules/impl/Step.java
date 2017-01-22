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

import org.panthercode.arctic.core.processing.ProcessException;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.settings.Context;

//TODO: update documentation

/**
 * The step class is used to execute client code. By overriding the method step() the user is able to execute its own
 * implementation.
 * <p>
 * If you inherit form this class, you should override stop() method, because the original
 * one only try to set process state to "Stopped". Therefore it will not challenge the process to stop.
 * <p>
 * It's on your own responsibility to change process state at runtime! E. g. if process waits, change process state to
 * "Waiting". When process goes on, change state to "Running". Otherwise maybe you will provoke bad behavior of the
 * system.
 *
 * @author PantherCode
 * @see org.panthercode.arctic.core.processing.modules.Module
 * @since 1.0
 */
public abstract class Step extends ModuleImpl {

    /**
     * Standard Constructor
     *
     * @throws NullPointerException Is thrown if value of identity is null.
     */
    public Step()
            throws NullPointerException {
        this(new Context());
    }

    /**
     * Constructor
     *
     * @param context context the object is associated with
     * @throws NullPointerException Is thrown if value of identity is null.
     */
    public Step(Context context)
            throws NullPointerException {
        super(context);
    }

    /**
     * Copy Constructor
     *
     * @param step object to copy
     * @throws NullPointerException Is thrown if value of parameter is null.
     */
    public Step(Step step)
            throws NullPointerException {
        super(step);
    }

    /**
     * Call step() method and checks returned value. If value is <tt>true</tt> the process state is changed to
     * "Succeeded"; Otherwise to "Failed". If an exceptions is thrown, the object try to set the state to "Failed".
     *
     * @throws IllegalStateException Is thrown if object's process can't set to 'Succeeded' or 'Failed'.
     * @throws ProcessException      Is thrown if an error occurred while running the step.
     */
    @Override
    public synchronized boolean start()
            throws ProcessException {
        if (this.changeState(ProcessState.RUNNING)) {

            try {
                ProcessState result = this.step() ? ProcessState.SUCCEEDED : ProcessState.FAILED;

                return this.changeState(result);
            } catch (ProcessException e) {
                this.changeState(ProcessState.FAILED);

                throw new ProcessException("An error has occurred while executing the step module.", e);
            }
        }

        return false;
    }

    /**
     * This method is called by start() method. It contains concrete implemented code of some functionality.
     *
     * @return Returns <tt>true</tt> if the actual run was successful; Otherwise <tt>false</tt>.
     * @throws ProcessException Is thrown if an error occurred while running the step.
     */
    public abstract boolean step() throws ProcessException;

    public abstract Step copy() throws UnsupportedOperationException;
}