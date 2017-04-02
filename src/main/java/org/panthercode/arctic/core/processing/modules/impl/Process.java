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

import java.util.Iterator;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessException;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.settings.Context;

/**
 * The Process class runs one element afterRun another.
 *
 * @author PantherCode
 * @see Module
 * @since 1.0
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
     * @return Returns a actual running module or null if processById doesn't run.
     */
    public Module getCurrentModule() {
        return this.currentModule;
    }

    /**
     * Starts the processById.
     *
     * @throws Exception Is eventually thrown by actual module or if an error occurred while running
     * processById.
     */
    @Override
    public boolean start(Object[] args)
        throws ProcessException {
        if (this.changeState(ProcessState.RUNNING)) {

            this.before();

            try {
                Iterator<Module> iterator = this.iterator();
                while (iterator.hasNext() && this.isRunning()) {
                    this.currentModule = iterator.next();

                    this.currentModule.start(args);

                    if (!this.currentModule.isSucceeded()) {
                        this.currentModule = null;

                        return false;
                    }
                }

                this.currentModule = null;

                this.changeState(ProcessState.SUCCEEDED);

                this.after();

                return true;
            } catch (Exception e) {
                this.changeState(ProcessState.FAILED);
                this.after();
                throw new ProcessException("While running the processById module an error is occurred.",
                    e);
            }
        }

        return false;
    }

    /**
     * Stops the actual processById. It's not guaranteed that processById stops immediately, but afterRun
     * finishing actual module. Calls actual module's <tt>stop()</tt> method.
     *
     * @throws Exception Is eventually thrown by actual module or if an error occurred while
     * stopping processById.
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

    @Override
    public boolean hasResult() {
        return false;
    }

    @Override
    public Object result() {
        return null;
    }

    /**
     * Returns a toHash code value of this object.
     *
     * @return Returns a toHash code value of this object.
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
