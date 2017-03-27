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
import org.panthercode.arctic.core.processing.ProcessException;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.modules.Module;
import org.panthercode.arctic.core.processing.modules.helper.Controller;
import org.panthercode.arctic.core.processing.modules.helper.RepeaterOptions;
import org.panthercode.arctic.core.settings.Context;

import java.util.concurrent.TimeUnit;

//TODO: update documentation

/**
 * Class to repeat module's functionality. The Loop class provides some basic functions to control the loop process.
 *
 * @author PantherCode
 * @see Module
 * @since 1.0
 */
public abstract class Repeater extends ModuleImpl {

    /**
     * Module for processing
     */
    private Module module = null;

    /**
     *
     */
    protected RepeaterOptions options;

    /**
     *
     */
    private Controller<? extends Object> controller;

    /**
     * Loop class use milliseconds for measurements
     */
    protected final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException Is thrown if value of module or identity is null.
     */
    public Repeater(Module module)
            throws NullPointerException {
        this(module, new RepeaterOptions());
    }

    /**
     * Constructor
     *
     * @param module module for processing
     * @throws NullPointerException Is thrown if value of module or identity is null.
     */
    public Repeater(Module module,
                    RepeaterOptions options)
            throws NullPointerException {
        this(module, options, null);
    }

    /**
     * Constructor
     *
     * @param module  module for processing
     * @param context context the object is associated with
     * @throws NullPointerException Is thrown if value of module or identity is null.
     *                              throws IllegalArgumentException
     */
    public Repeater(Module module,
                    RepeaterOptions options,
                    Context context)
            throws NullPointerException {
        super(context);

        ArgumentUtils.checkNotNull(options, "options");

        this.setModule(module);

        this.setController(this.createController());

        this.options = options;
    }

    /**
     * Copy Constructor
     *
     * @param repeater object to copy
     * @throws NullPointerException          Is thrown if value of parameter is null.
     * @throws UnsupportedOperationException Is thrown if child element doesn't support cloning.
     */
    public Repeater(Repeater repeater)
            throws NullPointerException, UnsupportedOperationException {
        super(repeater);

        this.setModule(repeater.getModule().copy());

        this.setController(this.createController());

        this.options = new RepeaterOptions(repeater.getDelayTime(), repeater.isIgnoreExceptions(), repeater.canQuit());
    }

    private void setController(Controller<? extends Object> controller) {
        ArgumentUtils.checkNotNull(controller, "controller");

        this.controller = controller;
    }

    /**
     * Set new context the object is associated with. It also sets the context of child element. You can only set a new
     * context to this object if process state isn't "Running" or "Waiting".
     *
     * @param context new context
     * @return
     */
    @Override
    public synchronized boolean setContext(final Context context) {
        return super.setContext(context) && this.module.setContext(context);
    }

    /**
     * Set a module the object is associated with. You can only set a new module, if objects process state is "Ready".
     *
     * @param module new module for processing
     * @return
     * @throws NullPointerException Is thrown if module parameter has value <tt>null</tt>
     */
    public synchronized boolean setModule(final Module module)
            throws NullPointerException {
        if (this.isReady()) {
            ArgumentUtils.checkNotNull(module, "module");

            this.module = module;

            this.module.setContext(this.getContext());

            return true;
        }

        return false;
    }

    /**
     * Returns the module the object is associated with.
     *
     * @return Returns the module the object is associated with.
     */
    public Module getModule() {
        return this.module;
    }

    /**
     * Set a new delay time the object is associated with. The value must be greater than or equals zero.
     *
     * @param durationInMillis new timeout
     * @throws IllegalArgumentException Is thrown if value of parameter is less than zero.
     */
    public synchronized void setDelayTime(long durationInMillis)
            throws IllegalArgumentException {
        this.options.setDelayTime(durationInMillis);
    }

    /**
     * Set a new delay time the object is associated with. The value must be greater than or equals zero.
     *
     * @param unit     time unit of duration
     * @param duration new timeout value
     * @throws IllegalArgumentException Is thrown if value of duration is less than zero.
     * @throws NullPointerException
     */
    public synchronized void setDelayTime(TimeUnit unit, long duration)
            throws NullPointerException, IllegalArgumentException {
        ArgumentUtils.checkNotNull(unit, "time unit");

        this.setDelayTime(unit.toMillis(duration));
    }

    /**
     * Returns the actual delay time the object is associated with.
     *
     * @return Returns the actual delay time the object is associated with.
     */
    public long getDelayTime() {
        return this.options.getDelayTime();
    }

    /**
     * Returns the actual delay time the object is associated with.
     *
     * @param unit own time unit
     * @return Returns the actual delay time the object is associated with.
     * @throws NullPointerException
     */
    public long getDelayTime(final TimeUnit unit) {
        ArgumentUtils.checkNotNull(timeUnit, "timeUnit");

        return unit.convert(this.options.getDelayTime(), this.timeUnit);
    }

    /**
     * Returns a flag representing whether the object ignores exceptions are thrown by module or not.
     *
     * @return Returns <tt>true</tt> if object ignores exceptions; Otherwise <tt>false</tt>.
     */
    public boolean isIgnoreExceptions() {
        return this.options.isIgnoreExceptions();
    }

    /**
     * Set a flag to ignore exceptions thrown by value or not.
     *
     * @param ignoreExceptions flag tp ignore exceptions
     */
    public synchronized void ignoreExceptions(boolean ignoreExceptions) {
        this.options.ignoreExceptions(ignoreExceptions);
    }

    /**
     * @return
     */
    public boolean canQuit() {
        return this.options.canQuit();
    }

    /**
     * @param canQuit
     */
    public synchronized void canQuit(boolean canQuit) {
        this.options.canQuit(canQuit);
    }

    /**
     * This method will be called beforeRun loop process starts.
     */
    public synchronized void before()
            throws ProcessException {
    }

    /**
     * This method will be called afterRun loop process finished.
     */
    public synchronized void after()
            throws ProcessException {
    }

    @Override
    public synchronized boolean start(Object[] args) throws ProcessException {
        if (this.changeState(ProcessState.RUNNING)) {
            before();

            for (controller.reset(); controller.accept(); controller.update()) {
                this.module.reset();

                try {
                    this.module.start(args);

                    if ((module.isSucceeded() && this.canQuit()) || this.isStopped()) {
                        break;
                    }

                    Thread.sleep(this.getDelayTime());
                } catch (ProcessException e) {
                    if (!this.isIgnoreExceptions()) {
                        this.changeState(ProcessState.FAILED);
                        throw new ProcessException("While running the module an error occurred.", e);
                    }
                } catch (InterruptedException e) {
                    throw new ProcessException(e);
                }
            }

            if (!this.isStopped()) {
                ProcessState result = (!this.canQuit() || this.module.isSucceeded()) ? ProcessState.SUCCEEDED
                        : ProcessState.FAILED;

                if (!this.changeState(result)) {
                    throw new ProcessException("Failed to set status to " + result + ".");
                }
            }

            this.after();

            return !this.isStopped();
        }

        return false;
    }

    /**
     * @return
     * @throws ProcessException
     */
    @Override
    public synchronized boolean stop()
            throws ProcessException {
        return this.changeState(ProcessState.STOPPED) && this.module.stop();
    }

    /**
     * @return
     * @throws ProcessException
     */
    @Override
    public synchronized boolean reset()
            throws ProcessException {
        return this.changeState(ProcessState.READY) && this.module.reset();
    }

    /**
     * Returns a toHash code value of this object.
     *
     * @return Returns a toHash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(super.hashCode())
                .append(this.module.hashCode())
                .append(this.getDelayTime())
                .append(this.isIgnoreExceptions())
                .append(this.canQuit())
                .toHashCode());
    }

    /**
     * Checks if this object is equals to another one.
     *
     * @param obj other object for comparison
     * @return Returns <tt>true</tt> if both objects are equal; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Repeater)) {
            return false;
        }

        Repeater repeater = (Repeater) obj;
        return super.equals(repeater) &&
                this.module.equals(repeater.getModule()) &&
                this.getDelayTime() == repeater.getDelayTime() &&
                this.isIgnoreExceptions() == repeater.isIgnoreExceptions() &&
                this.canQuit() == repeater.canQuit();
    }

    /**
     * Creates a new controller instance to control the repeating process.
     *
     * @return Returns a new instance of a controller.
     */
    protected abstract Controller<? extends Object> createController();
}
