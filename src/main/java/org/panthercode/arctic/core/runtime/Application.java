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
package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.settings.Settings;

import java.io.InputStream;
import java.net.URL;

/**
 * TODO: class documentation
 *
 * @author PantherCode
 */
@IdentityInfo(name = "Application")
@VersionInfo(major = 1)
public abstract class Application {

    /**
     * actual instance of application object
     */
    private static Application instance = null;

    /**
     * identity of application
     */
    private final Identity identity;

    /**
     * version of application
     */
    private final Version version;

    /**
     * Default Constructor
     */
    protected Application() {
        if (Identity.isAnnotated(this.getClass())) {
            this.identity = Identity.fromAnnotation(this.getClass());
        } else {
            this.identity = Identity.generate();
        }

        if (Version.isAnnotated(this.getClass())) {
            this.version = Version.fromAnnotation(this.getClass());
        } else {
            this.version = new Version();
        }
    }

    /**
     * Returns the version of the application.
     *
     * @return Returns the version of the application.
     */
    public Version version() {
        return this.version.copy();
    }

    /**
     * Returns the identity of the application
     *
     * @return Returns the identity of the application
     */
    public Identity identity() {
        return this.identity.copy();
    }

    /**
     * Returns the actual instance of settings object.
     *
     * @return Returns the actual instance of settings object.
     */
    public Settings settings() {
        return Settings.instance();
    }

    /**
     * Checks if the a given resource exists or not.
     *
     * @param name name of resource
     * @return Returns <tt>true</tt> if the resource exists; Otherwise <tt>false</tt>.
     */
    public boolean hasResource(String name) {
        return this.resource(name) != null;
    }

    /**
     * Returns the address to a given resource.
     *
     * @param name name of resource
     * @return Returns the address to a given resource.
     */
    public URL resource(String name) {
        ArgumentUtils.assertNotNull(name, "resource name");

        return Thread.currentThread().getContextClassLoader().getResource(name);
    }

    /**
     * Returns a stream to a given resource.
     *
     * @param name name of resource
     * @return Returns a stream to a given resource.
     */
    public InputStream resourceAsInputStream(String name) {
        ArgumentUtils.assertNotNull(name, "resource name");

        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    /**
     * Throws an exception to force the application to close.
     *
     * @throws ShutdownException Is thrown when this method is called.
     */
    public void shutdown()
            throws ShutdownException {
        throw new ShutdownException();
    }

    /**
     * Throws an exception to force the application to close.
     *
     * @param message message of the exception
     * @throws ShutdownException Is thrown when this method is called.
     */
    public void shutdown(String message)
            throws ShutdownException {
        throw new ShutdownException(message);
    }

    /**
     * Throws an exception to force the application to close.
     *
     * @param cause cause of the exception
     * @throws ShutdownException Is thrown when this method is called.
     */
    public void shutdown(Throwable cause)
            throws ShutdownException {
        throw new ShutdownException(cause);
    }

    /**
     * Throws an exception to force the application to close.
     *
     * @param message message of the exception
     * @param cause   cause of the exception
     * @throws ShutdownException Is thron when this method is called.
     */
    public void shutdown(String message, Throwable cause)
            throws ShutdownException {
        throw new ShutdownException(message, cause);
    }

    /**
     * Method to start the application.
     *
     * @param app  actual instance of an application class
     * @param args (commandline) arguments
     */
    public static void startUp(Application app, String[] args) {
        ArgumentUtils.assertNotNull(app, "application");

        instance = app;

        args = args == null ? new String[0] : args;

        try {
            app.run(args);
        } catch (ShutdownException e) {
            app.shutdownHandler(e);
        } catch (Exception e) {
            app.exceptionHandler(e);
        }
    }

    /**
     * Returns the actual instance of the <tt>Application</tt> class object.
     *
     * @return Returns the actual instance of the <tt>Application</tt> class object.
     */
    public static Application current() {
        if (instance == null) {
            throw new RuntimeException("The appliction is not running.");
        }

        return instance;
    }

    /**
     * TODO: documentation
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Application> T current(Class<T> clazz) {
        return (T) current();
    }

    /**
     * Method with functionality, that will executed after starting the application.
     *
     * @param args (commandline) arguments
     * @throws Exception Is thrown if an error is occurred at runtime.
     */
    public abstract void run(String[] args) throws Exception;

    /**
     * Handles all unhandled exceptions at runtime to avoid an uncontrolled crash of the application.
     *
     * @param e unhandled exception
     */
    public abstract void exceptionHandler(Exception e);

    /**
     * Handles all <tt>ShutdownException</tt> at runtime to avoid an uncontrolled crash of the  application.
     *
     * @param e actual exception
     */
    public abstract void shutdownHandler(ShutdownException e);
}
