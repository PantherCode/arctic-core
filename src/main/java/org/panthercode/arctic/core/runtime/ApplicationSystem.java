package org.panthercode.arctic.core.runtime;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;

/**
 * Created by architect on 05.03.17.
 */
@IdentityInfo(name = "ApplicationSystem")
@VersionInfo(major = 1, minor = 0)
public abstract class ApplicationSystem<T extends Kernel> extends Application {

    /**
     * actual instance of application object
     */
    private static ApplicationSystem instance = null;

    /**
     *
     */
    private final T kernel;

    /**
     *
     * @param kernel
     */
    protected ApplicationSystem(T kernel) {
        super();

        this.kernel = ArgumentUtils.checkNotNull(kernel, "kernel");
    }

    /**
     *
     * @return
     */
    public T kernel() {
        return this.kernel;
    }

    /**
     * Method to start the application.
     *
     * @param application actual instance of an application class
     * @param args        (commandline) arguments
     */
    public static void start(ApplicationSystem application, String[] args) {
        instance = ArgumentUtils.checkNotNull(application, "application");

        args = (args == null) ? new String[0] : args;

        try {
            application.initialize(args);

            application.run(args);
        } catch (Exception e) {
            application.exceptionHandler(e);
        }
    }

    /**
     * Returns the actual instance of the <tt>Application</tt> class object.
     *
     * @return Returns the actual instance of the <tt>Application</tt> class object.
     */
    public static ApplicationSystem current() {
        if (instance == null) {
            throw new RuntimeException("The application is not running.");
        }

        return instance;
    }

    /**
     * TODO: documentation
     */
    public static <T extends Application> T current(Class<T> clazz) {
        return clazz.cast(current());
    }

    /**
     *
     * @return
     */
    public static Kernel currentKernel() {
        return current().kernel();
    }

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Kernel> T currentKernel(Class<T> clazz) {
        return clazz.cast(currentKernel());
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public abstract void initialize(String[] args) throws Exception;
}