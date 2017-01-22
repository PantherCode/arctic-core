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
package org.panthercode.arctic.core.processing.modules;

import org.panthercode.arctic.core.helper.identity.Identifiable;
import org.panthercode.arctic.core.helper.version.Versionable;
import org.panthercode.arctic.core.processing.ProcessException;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.settings.Context;


//TODO: udpate documentation
//TODO: explain  difference betwesen return boolean and process state!

/**
 * A module is the basic unit for execution. It provides a little set of functionality for controlling and handling the
 * object at runtime. The concrete implementation should always contains a coherent inner state model, that
 * change the state corresponding to the actual phase of the object.
 * <p>
 * For better safety there is no setter function to change the inner process state directly. Instead changes should
 * be hidden and done by corresponded methods automatically. E.g. the <tt>start()</tt> method change the state to
 * <tt>Running</tt>.
 * <p>
 * There is also no way to let wait the object explicitly, because the "wait" state is handle a sub state of running.
 * Means, if the module is executing, in state "running", and can't go on because the needed resource is not available
 * for example the state jumps form "running" to "waiting" and back if executions will go on.
 * <p>
 * Although the <tt>ProcessState</tt> enumeration provides some more possible states, the module provides only a
 * meaningful subset to maintain processing by default.
 * <p>
 * To handle all kind of exceptions an implementation can bring with, important functions contains the general <tt>
 * Exception</tt> class type. It's a little bit ugly to handle with and in some cases it's annoying to extract the right
 * one. But on the hand this way still grantees a consistent handling of all kinds of exceptions and prevent annoying
 * compiler errors while overriding methods.
 * The following examples shows nether the less a little way around:
 * <pre>
 * {@code
 * public void start() throws Exception{
 *    try{
 *        // do something dangerous
 *    } catch(CorrectException e){
 *        // If more than one exceptions type is possible, you should
 *        // bundle them to a meaningful upper class type like
 *        // RuntimeException
 *        throw new Exception("Some dangerous things happened.", e);
 *    }
 * }
 * }
 * </pre>
 * In method, which called method <tt>start()</tt>, use double nested try-catch-blocks:
 * <pre>
 * {@code
 * try{
 *     //do dangerous things
 *
 *     try{
 *         module.start()
 *     } catch(Exception e) {
 *         if(e.cause != null){
 *             throw (ConcreteException) e.getCause(); // rethrow correct Exception
 *         }
 *     }
 *
 * } catch(ConcreteException e) {
 *     // exceptions handling
 * } catch(Exception e){
 *     // catch the rest.
 * }
 * }
 * </pre>
 * By default a module is not thread safe programmed. If this requirement is necessary you should add <tt>synchronized
 * </tt> to function signature of all inner field manipulating methods and <tt>copy()</tt>.
 * <p>
 * If you implement this interface or inherit from a module class, you should always override the following methods:
 * equals(), hashCode() and copy(). If you are not sure about that, you can look at existing implementations.
 *
 * @author PantherCode
 * @since 1.0
 */
public interface Module extends Identifiable, Versionable {

    /**
     * Returns the context the object is associated with.
     *
     * @return Returns the context the object is associated with.
     */
    Context getContext();

    /**
     * Set a new context the object is associated with.
     *
     * @param context new object's context.
     * @return
     */
    boolean setContext(Context context);

    /**
     * Returns whether the object is ready or not.
     *
     * @return Returns <tt>true</tt> if the object is ready; Otherwise <tt>false</tt>.
     */
    boolean isReady();

    /**
     * Returns whether the object is executing its implementation or not.
     *
     * @return Returns <tt>true</tt> if object is executing; Otherwise <tt>false</tt>.
     */
    boolean isRunning();

    /**
     * Returns whether the object is waiting for something or rather stop executing temporarily or not.
     *
     * @return Returns <tt>true</tt> if object is waiting; Otherwise <tt>false</tt>.
     */
    boolean isWaiting();

    /**
     * Returns whether the object is finished successful or not.
     *
     * @return Returns <tt>true</tt> if object finished successful; Otherwise <tt>false</tt>.
     */
    boolean isSucceeded();

    /**
     * Returns whether the object is finished unsuccessful or not.
     *
     * @return Returns <tt>true</tt> if object finished unsuccessful; Otherwise <tt>false</tt>.
     */
    boolean isFailed();

    /**
     * Return whether the process is aborted or not.
     *
     * @return Returns <tt>true</tt> if object aborts the process; Otherwise <tt>false</tt>.
     */
    boolean isStopped();

    /**
     * Returns the actual process state associated with the object.
     *
     * @return Returns the actual process state associated with the object.
     */
    ProcessState state();

    /**
     * Executes the containing functionality of the object. This function should only entered in state "ready". If so the
     * function should change the state to "running" after start the execution. After finishing execution set process state
     * to "successful" if the run ends with the expected result; Otherwise set to "failed".
     *
     * @throws ProcessException Is eventually thrown by the concrete implementation of this interface.
     */
    boolean start() throws ProcessException;

    /**
     * To abort the actual run call the stop() method. The process state is change to "stopped".
     *
     * @throws ProcessException Is eventually thrown by the concrete implementation of this interface.
     */
    boolean stop() throws ProcessException;

    /**
     * This method set the process state to "ready". It's an good place to take trash out and prepare the next run.
     *
     * @throws ProcessException Is eventually thrown by the concrete implementation of this interface.
     */
    boolean reset() throws ProcessException;

    /**
     * Creates a new deep copy of an object.
     *
     * @return Returns a new identical instance of the object.
     * @throws UnsupportedOperationException Is thrown if the object doesn't support copying.
     */
    Module copy() throws UnsupportedOperationException;
}
