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

import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.exception.ProcessException;
import org.panthercode.arctic.core.settings.context.Context;

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
 */
public abstract class Step extends ModuleImpl {

    /**
     * Standard Constructor
     *
     * @throws NullPointerException Is thrown if value of identity is null.
     */
    public Step()
            throws NullPointerException {
        this((Context) null);
    }

    /**
     * Constructor
     *
     * @param context context the object is associated with
     * @throws NullPointerException Is thrown if value of identity is null.
     */
    public Step(Context context)
            throws NullPointerException {
        this(null, context);
    }

    /**
     * Constructor
     *
     * @param identity identity the object is associated with
     * @param context  context the object is associated with
     * @throws NullPointerException Is thrown if value of identity is null.
     */
    public Step(Identity identity, Context context)
            throws NullPointerException {
        super(identity, context);
    }

    /**
     * Copy Constructor
     *
     * @param step object to copy
     * @throws CloneNotSupportedException Is thrown if a module in bundle doesn't support cloning.
     * @throws NullPointerException       Is thrown if value of parameter is null.
     */
    public Step(Step step)
            throws NullPointerException, CloneNotSupportedException {
        super(step);
    }

    /**
     * Call step() method and checks returned value. If value is <tt>true</tt> the process state is changed to
     * "Succeeded"; Otherwise to "Failed". If an exception is thrown, the object try to set the state to "Failed".
     *
     * @throws IllegalStateException Is thrown if object's process can't set to 'Succeeded' or 'Failed'.
     * @throws ProcessException      Is thrown if an error occurred while running the step.
     */
    @Override
    public synchronized boolean start()
            throws ProcessException, IllegalStateException {
        if(super.start()) {

            try {
                ProcessState result = this.step() ? ProcessState.SUCCEEDED : ProcessState.FAILED;

                this.changeState(result);

                return true;
            } catch (Exception e) {
                if (this.canChangeState(ProcessState.FAILED)) {
                    this.changeState(ProcessState.FAILED);
                }

                throw new ProcessException("While running process step an error is occurred.", e);
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
    public abstract boolean step()
            throws ProcessException;
}