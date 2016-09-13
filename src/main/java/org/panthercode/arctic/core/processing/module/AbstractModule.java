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
package org.panthercode.arctic.core.processing.module;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.ProcessStateHandler;
import org.panthercode.arctic.core.settings.context.Context;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * The Abstract Module class is the root element in inheritance hierarchy. This module contains basic functionality all
 * further child modules have together like getter functions to indicate inner state, identity, version, and some more.
 * <p>
 * All modules, which inherit directly or indirectly from this class, share the same state automate for phase
 * controlling. The following table shows allowed state changes. The left column represents the old state and top row
 * the new one. If value is <tt>true</tt> the change is allowed; Otherwise not.
 * <pre>
 * ---------------------------------------------------------
 * from \ to  Ready Running Waiting Succeeded Failed Stopped
 * ---------------------------------------------------------
 * Ready       -     true    false    false   false   true
 *
 * Running   false     -     true     true     true   true
 *
 * Waiting   false   true      -      false    false  true
 *
 * Succeeded true    false   false      -      true   false
 *
 * Failed    true    false   false    true      -     false
 *
 * Stopped   true    false   false    false    false    -
 * ---------------------------------------------------------
 * </pre>
 * <p>
 * Another important point are identities. Every class, who inherits from this class, need an identity. There are two
 * possibilities to handle. First you can set an identity as parameter via constructor. Second way you annotate child
 * class with @IdentityInfo and constructor will read the information automatically. You always should prefer second
 * way, because is safer and prevent the situation to forget it. Some principle obtains to versions. It's not a must
 * have to set a version, but nevertheless do it.
 * <p>
 * If you inherit directly or indirectly from this class, you always should call in constructor super(...) with correct
 * parametrization to initialize the inner state machine and other important fields. Don't ask, do it! If you are not
 * sure how to do it, look at other module classes. Attention: NEVER NEVER EVER use uninitialized modules for processing.
 * In complex situations maybe it's really hard to find faulty position in code and secondly the hole process can crash
 * down.
 * <p>
 * Modules are designed for usage in multi-threaded environments. Therefore all setter or inner member manipulating
 * functions signatures use <tt>synchronized</tt> keyword. If you override an method or add your own code, you should
 * do same to avoid incorrect behavior of object at runtime.
 * <p>
 * Please read the documentation of child modules. If you inherit from any class, inherit always from the most
 * specialist one to your needs. Always prefer to use available classes.
 */
public abstract class AbstractModule implements Module {

    /**
     * The local context the object is associated with.
     */
    protected Context context;

    /**
     * The identity the object is associated with.
     */
    private final Identity identity;

    /**
     * Representation of object's inner state
     */
    private ProcessState actualState;

    /**
     * The version the object is associated with.
     */
    private final Version version;

    /**
     * Actual PrcessStateHandler to handle state transitions
     */
    private ProcessStateHandler processStateHandler = null;

    /**
     * Contains all allowed process state changes.
     */
    protected HashMap<ProcessState, EnumSet<ProcessState>> stateMap;

    /**
     * Standard Constructor
     */
    public AbstractModule()
            throws NullPointerException {
        this(new Context());
    }

    /**
     * Constructor
     *
     * @param context new context of object
     */
    public AbstractModule(Context context)
            throws NullPointerException {
        this(null, context);
    }

    /**
     * Constructor
     *
     * @param identity identity the module is associated with.
     * @param context  context the module is associated with.
     * @throws NullPointerException
     */
    public AbstractModule(Identity identity, Context context)
            throws NullPointerException {
        if (identity != null) {
            this.identity = identity;
        } else {
            if (Identity.isAnnotated(this)) {
                this.identity = Identity.fromAnnotation(this);
            } else {
                throw new NullPointerException("The identity is null.");
            }
        }

        if (Version.isAnnotated(this)) {
            this.version = Version.fromAnnotation(this);
        } else {
            this.version = new Version();
        }

        this.actualState = ProcessState.READY;

        this.setContext(context);

        this.initialiseStateMap();
    }

    /**
     * Copy Constructor
     *
     * @param module object to copy
     */
    public AbstractModule(AbstractModule module)
            throws NullPointerException {
        ArgumentUtils.assertNotNull(module, "module");

        this.identity = Identity.generate(module.identity().getName(),
                module.identity().getGroup());

        this.actualState = ProcessState.READY;

        this.version = module.version();

        this.setContext(module.getContext());

        this.initialiseStateMap();
    }

    /**
     * Returns the identity the object is associated with.
     *
     * @return Returns the identity the object is associated with.
     */
    public Identity identity() {
        return Identity.generate(this.identity.getName(), this.identity.getGroup());
    }

    /**
     * Returns the identity the object is associated with.
     *
     * @return Returns the version the object is associated with.
     */
    public Version version() {
        return new Version(this.version);
    }

    /**
     * Returns the context the object is associated with.
     *
     * @return Returns the context the object is associated with.
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Set new context the object is associated with.
     *
     * @param context new context
     */
    public synchronized void setContext(final Context context) {
        if (!this.isRunning() && !this.isWaiting()) {
            this.context = (context == null) ? new Context() : context;
        }
    }

    /**
     * Returns the actual <tt>ProcessStateHandler</tt> the object is associated with.
     *
     * @return Returns the actual <tt>ProcessStateHandler</tt> the object is associated with.
     */
    public ProcessStateHandler getProcessStateHandler() {
        return this.processStateHandler;
    }

    //TODO: expand model for multi-handler processing

    /**
     * Set a new <tt>ProcessStatusHandler</tt> the object is associated with.
     *
     * @param handler new handler
     */
    public synchronized void setProcessStateHandler(ProcessStateHandler handler) {
        this.processStateHandler = handler;
    }

    /**
     * Returns the inner state the object is associated with.
     *
     * @return Returns the inner state the object is associated with.
     */
    public ProcessState state() {
        return this.actualState;
    }

    /**
     * Function to check whether the new state is allowed or not.
     *
     * @param newState new state the object would like to set
     * @return Returns <tt>true</tt> if the new state is allowed; Otherwise <tt>false</tt>.
     */
    public boolean canChangeState(final ProcessState newState) {
        return this.stateMap.containsKey(this.actualState) &&
                this.stateMap.get(this.actualState).contains(newState);
    }

    /**
     * Returns whether the object is ready or not.
     *
     * @return Returns <tt>true</tt> if the object is ready; Otherwise <tt>false</tt>.
     */
    public boolean isReady() {
        return this.actualState == ProcessState.READY;
    }

    /**
     * Returns whether the object is executing its implementation or not.
     *
     * @return Returns <tt>true</tt> if object is executing; Otherwise <tt>false</tt>.
     */
    public boolean isRunning() {
        return this.actualState == ProcessState.RUNNING;
    }

    /**
     * Returns whether the object is finished unsuccessful or not.
     *
     * @return Returns <tt>true</tt> if object finished unsuccessful; Otherwise <tt>false</tt>.
     */
    public boolean isFailed() {
        return this.actualState == ProcessState.FAILED;
    }

    /**
     * Return whether the process is aborted or not.
     *
     * @return Returns <tt>true</tt> if object aborts the process; Otherwise <tt>false</tt>.
     */
    public boolean isStopped() {
        return this.actualState == ProcessState.STOPPED;
    }

    /**
     * Returns whether the object is waiting for something or rather stop executing temporarily or not.
     *
     * @return Returns <tt>true</tt> if object is waiting; Otherwise <tt>false</tt>.
     */
    public boolean isWaiting() {
        return this.actualState == ProcessState.WAITING;
    }

    /**
     * Returns whether the object is finished successful or not.
     *
     * @return Returns <tt>true</tt> if object finished successful; Otherwise <tt>false</tt>.
     */
    public boolean isSucceeded() {
        return this.actualState == ProcessState.SUCCEEDED;
    }

    /**
     * Starts to run the object.
     *
     * @throws Exception Is eventually thrown by the concrete implementation.
     */
    public synchronized void start()
            throws Exception {
        if(this.isReady()){
            this.changeState(ProcessState.RUNNING);
        }
    }

    /**
     * Stops the actual run.
     *
     * @throws Exception Is eventually thrown by the concrete implementation.
     */
    public synchronized void stop()
            throws Exception{
        if(this.canChangeState(ProcessState.STOPPED)){
            this.changeState(ProcessState.STOPPED);
        }
    }

    /**
     * Set the inner stae to "ready". This method can only called if object has inner state "SUCCEEDED", "FAILED" or
     * "STOPPED".
     */
    public synchronized void reset()
            throws Exception {
        if (this.canChangeState(ProcessState.READY)) {
            this.changeState(ProcessState.READY);
        }

        //TODO: eventually throw RuntimeException, because wrong state
    }

    /**
     * Returns a hash code value of this object.
     *
     * @return Returns a hash code value of this object.
     */
    @Override
    public int hashCode() {
        return Math.abs(new HashCodeBuilder()
                .append(this.identity)
                .append(this.version)
                .append(this.actualState)
                .append(this.context)
                .toHashCode());
    }

    /**
     * Checks if this object is equals to another one. Both objects are equal if they identity equals method, version
     * equals() method, context equals() methods return <tt>true</tt> and actual state is equal.
     *
     * @param obj other object to check
     * @return Returns <code>true</code> if both objects have same member values; Otherwise <tt>false</tt>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof AbstractModule)) {
            return false;
        }

        AbstractModule module = (AbstractModule) obj;

        return this.identity.equals(module.identity()) &&
                this.version.equals(module.version) &&
                this.actualState == module.actualState &&
                this.context.equals(module.context);
    }

    /**
     * Returns a string representation of the object. The representation contains the id, name and group name.
     *
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return this.identity.toString() + ", version = " + this.version.toString();
    }

    /**
     * Creates a copy of this object.
     *
     * @return Return a copy of this object.
     * @throws CloneNotSupportedException Is thrown if object doesn't provide an implementation of <tt>clone()</tt>
     *                                    method.
     */
    public abstract AbstractModule clone() throws CloneNotSupportedException;

    /**
     * Set the inner state of object to given value. If <tt>ProcessStateHandler</tt> is set, an event is raised.
     *
     * @param newState new state of object
     * @throws Exception Is eventually thrown by ProcessStateHandler implementation.
     */
    protected synchronized void changeState(final ProcessState newState)
            throws Exception {
        if (this.canChangeState(newState)) {
            ProcessState oldState = this.actualState;

            this.actualState = newState;

            if (this.processStateHandler != null) {
                this.processStateHandler.handle(this, oldState);
            }
        } else {
            //Todo: implement ProcessStateException
            //throw new ProcessStateException("Try to change from " + this.actualState + " to " + newState);
        }
    }

    /**
     * Helper function to fill a map with all allowed state changes. The map contains all state-pairs from table above,
     * which transition reutrns <tt>true</tt>.
     * <p>
     * If you override this method, also override canChangeState() method to avoid wrong behavior of object at runtime!
     */
    protected synchronized void initialiseStateMap() {
        if (this.stateMap == null) {
            this.stateMap = new HashMap<>(6);
        } else {
            this.stateMap.clear();
        }

        this.stateMap.put(ProcessState.READY, EnumSet.of(ProcessState.RUNNING, ProcessState.STOPPED));
        this.stateMap.put(ProcessState.RUNNING, EnumSet.of(ProcessState.WAITING, ProcessState.SUCCEEDED,
                ProcessState.FAILED, ProcessState.STOPPED));
        this.stateMap.put(ProcessState.WAITING, EnumSet.of(ProcessState.RUNNING, ProcessState.STOPPED));
        this.stateMap.put(ProcessState.SUCCEEDED, EnumSet.of(ProcessState.READY, ProcessState.FAILED));
        this.stateMap.put(ProcessState.FAILED, EnumSet.of(ProcessState.READY, ProcessState.SUCCEEDED));
        this.stateMap.put(ProcessState.STOPPED, EnumSet.of(ProcessState.READY));
    }
}
