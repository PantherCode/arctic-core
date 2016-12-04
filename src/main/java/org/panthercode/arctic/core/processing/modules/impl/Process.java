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

import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.exceptions.ProcessException;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.settings.context.Context;

import java.util.Iterator;

/**
 * The Process class runs one element after another.
 */
@IdentityInfo(name = "Standard Process", group = "Process Module")
@VersionInfo(major = 1)
public class Process extends Bundle {

    /**
     * actual running module
     */
    private Module currentModule = null;

    /**
     * Standard Constructor
     */
    public Process() {
        super();
    }

    /**
     * Constructor
     *
     * @param context context the module is associated with.
     */
    public Process(Context context) {
        super(context);
    }

    /**
     * Copy constructor
     *
     * @param process object to copy
     */
    public Process(Process process)
            throws NullPointerException {
        super(process);
    }

    /**
     * Returns a actual running module.
     *
     * @return Returns a actual running module or null if process doesn't run.
     */
    public Module getCurrentModule() {
        return this.currentModule;
    }

    /**
     * Starts the process.
     *
     * @throws Exception Is eventually thrown by actual module or if an error occurred while running process.
     */
    @Override
    public boolean start()
            throws ProcessException {
        if (this.changeState(ProcessState.RUNNING)) {

            this.before();

            try {
                Iterator<Module> iterator = this.iterator();
                while (iterator.hasNext() && this.isRunning()) {
                    this.currentModule = iterator.next();

                    this.currentModule.start();

                    if (!this.currentModule.isSucceeded()) {
                        this.currentModule = null;

                        //Todo: return false for whole process instead of throwing an exceptions
                        throw new RuntimeException("Step does not return successful.");
                    }
                }

                this.currentModule = null;

                this.changeState(ProcessState.SUCCEEDED);

                this.after();

                return true;
            } catch (Exception e) {
                this.changeState(ProcessState.FAILED);
                this.after();
            }
        }

        return false;
    }

    /**
     * Stops the actual process. It's not guaranteed that process stops immediately, but after finishing actual module.
     * Calls actual module's <tt>stop()</tt> method.
     *
     * @throws Exception Is eventually thrown by actual module or if an error occurred while stopping process.
     */
    public boolean stop()
            throws ProcessException {
        if (this.currentModule != null) {
            this.currentModule.stop();
        }

        return this.changeState(ProcessState.STOPPED);
    }

    @Override
    public boolean reset() throws ProcessException {
        // Todo: implement
        return false;
    }

    /**
     * Returns a hash code value of this object.
     *
     * @return Returns a hash code value of this object.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Checks if this object is equals to another one.
     *
     * @param obj other object for comparison
     * @return Returns <code>true</code> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Process)) {
            return false;
        }

        Process process = (Process) obj;

        return super.equals(process);
    }

    @Override
    public Process copy()
            throws UnsupportedOperationException {
        return new Process(this);
    }
}
