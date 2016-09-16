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
package org.panthercode.arctic.core.processing.container;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.annotation.IdentityInfo;
import org.panthercode.arctic.core.helper.version.annotation.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.modules.AbstractModule;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.settings.context.Context;

/**
 * Container are used as root elements for processing.
 */
@IdentityInfo(name = "Standard Container", group = "Container")
@VersionInfo(major = 1)
public class Container extends AbstractModule {

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
     * @param worker  module for processing
     * @param context context the module is associated with.
     */
    public Container(Module worker, Context context) {
        this(null, worker, context);
    }

    /**
     * Constructor
     *
     * @param identity identity the module is associated with.
     * @param worker   module for processing
     * @param context  context the module is associated with.
     */
    public Container(Identity identity, Module worker, Context context) {
        super(identity, context);

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

        this.setWorker(container.getWorker().clone());

        this.worker.setContext(container.context);
    }

    /**
     * Call the child module's <tt>start()</tt> method to begin the process
     *
     * @throws Exception Is eventually thrown by child module if an error occurred or if the container is in wrong
     *                   process state or actual child module is <tt>null</tt>.
     */
    @Override
    public synchronized void start()
            throws Exception {
        if (worker == null) {
            throw new NullPointerException("There is no worker to execute.");
        }

        super.start();

        worker.start();
    }

    /**
     * Stops the process by calling the child modules <tt>stop()</tt> method. Try to set the container's process state
     * to <tt>Stopped</tt>.
     */
    @Override
    public synchronized void stop()
            throws Exception {
        if (this.canChangeState(ProcessState.STOPPED)) {
            super.stop();

            worker.stop();
        }
    }

    /**
     * Sets a new worker the object is associated with.
     *
     * @param worker new worker the object is associated with
     */
    public synchronized void setWorker(final Module worker) {
        ArgumentUtils.assertNotNull(worker, "worker");

        if (this.isReady()) {
            this.worker = worker;
            this.worker.setContext(this.context);
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
    public synchronized void setContext(Context context) {
        if (!this.isRunning() && !this.isWaiting()) {
            context = context == null ? new Context() : context;

            super.setContext(context);

            if (this.worker != null) {
                this.worker.setContext(context);
            }
        }
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
}
