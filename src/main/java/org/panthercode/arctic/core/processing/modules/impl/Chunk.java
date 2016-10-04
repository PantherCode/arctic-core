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

import org.panthercode.arctic.core.helper.identity.annotation.IdentityInfo;
import org.panthercode.arctic.core.helper.version.annotation.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.exceptions.ProcessException;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.settings.context.Context;

//TODO: implement reset()

/**
 * The Chunk class runs all elements parallelised.
 */
@IdentityInfo(name = "Standard Chunk", group = "Chunk Module")
@VersionInfo(major = 1)
public class Chunk extends Bundle {

    /**
     * Standard Constructor
     */
    public Chunk() {
        super();
    }

    /**
     * Constructor
     *
     * @param context context the module is associated with.
     */
    public Chunk(Context context) {
        super(context);
    }

    /**
     * Copy Constructor
     *
     * @param chunk object to copy
     */
    public Chunk(Chunk chunk)
            throws UnsupportedOperationException, NullPointerException {
        super(chunk);
    }

    /**
     * Starts the process.
     *
     * @throws Exception Is eventually thrown by actual module or if an error occurred while running process.
     */
    public synchronized boolean start()
            throws ProcessException {
        // Todo: implement
        return false;
    }

    /**
     * Stops the actual process. It's not guaranteed that process stops immediately.
     * Calls the <tt>stop()</tt> method of each module.
     *
     * @throws Exception Is eventually thrown by actual module or if an error occurred while stopping process.
     */
    public synchronized boolean stop()
            throws ProcessException {
        for (Module module : this.modules()) {
            //Todo ignore exceptions, rethrow it after stopping all other modules
            module.stop();
        }

        return this.canChangeState(ProcessState.STOPPED);
    }

    /**
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public Chunk copy()
            throws UnsupportedOperationException {
        return new Chunk(this);
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
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Chunk)) {
            return false;
        }

        Chunk chunk = (Chunk) obj;

        return super.equals(chunk);
    }
}