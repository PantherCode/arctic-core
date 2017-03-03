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

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessException;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.settings.Context;

/**
 * Container are used as root elements for processing.
 *
 * @author PantherCode
 * @see Module
 * @since 1.0
 */
@IdentityInfo(name = "Standard Container", group = "Container")
@VersionInfo(major = 1)
public class Container extends ModuleImpl {

    /**
     * actual module fpr processing
     */
    private Module worker;

    /**
     * Constructor
     *
     * @param worker module for processing
     */
    public Container(Module worker) {
        this(worker, null);
    }

    /**
     * Constructor
     *
     * @param worker   module for processing
     * @param context  context the module is associated with.
     */
    public Container(Module worker, Context context) {
        super(context);

        this.setWorker(worker);
    }

    /**
     * Copy Constructor
     *
     * @param container object to copy
     * @throws CloneNotSupportedException Is thrown if child module doesn't support cloning.
     */
    public Container(Container container)
            throws CloneNotSupportedException {
        super(container);

        //this.setWorker(container.getWorker().clone());

      //  this.worker.setContext(container.context);
    }

    /**
     * Call the child module's <tt>start()</tt> method to begin the process
     *
     * @throws Exception Is eventually thrown by child module if an error occurred or if the container is in wrong
     *                   process state or actual child module is <tt>null</tt>.
     */
    @Override
    public synchronized boolean start()
            throws ProcessException {
        if (worker == null) {
            throw new NullPointerException("There is no worker to execute.");
        }

       // if(super.start()) {
         //   return this.worker.start();
        //}

        return false;
    }

    /**
     * Stops the process by calling the child modules <tt>stop()</tt> method. Try to set the container's process state
     * to <tt>Stopped</tt>.
     */
    @Override
    public synchronized boolean stop()
            throws ProcessException {
        if(worker != null){
            this.worker.stop();
        }

        return  false; //super.stop();
    }

    @Override
    public boolean reset() throws ProcessException {
        return false;
    }

    /**
     * Sets a new worker the object is associated with.
     *
     * @param worker new worker the object is associated with
     */
    public synchronized void setWorker(final Module worker) {
        ArgumentUtils.checkNotNull(worker, "worker");

        if (this.isReady()) {
            this.worker = worker;
           // this.worker.setContext(this.context);
        } else {
            throw new RuntimeException("The worker can only change in state 'Ready'.");
        }
    }

    /**
     * Returns the actual child module.
     *
     * @return Returns the actual child module.
     */
    public Module getWorker() {
        return this.worker;
    }

    /**
     * Sets a new context the object and child module is associated with.
     *
     * @param context new context the object and child module is associated with.
     */
    @Override
    public synchronized boolean setContext(Context context) {
        if (!this.isRunning() && !this.isWaiting()) {
            context = context == null ? new Context() : context;

            if(super.setContext(context)) {
                if (this.worker != null) {
                    return this.worker.setContext(context);
                }
            }
        }

        return false;
    }

    /**
     * Creates a copy of this object.
     *
     * @return Return a copy of this object.
     * @throws CloneNotSupportedException Is thrown if child module doesn't support cloning.
     */
    @Override
    public Container clone()
            throws CloneNotSupportedException {
        return new Container(this);
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
                .append(this.worker)
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

        if (!(obj instanceof Container)) {
            return false;
        }

        Container container = (Container) obj;

        return super.equals(container) && this.worker.equals(container.getWorker());
    }

    @Override
    public ModuleImpl copy() throws UnsupportedOperationException {
        return null;
    }
}
