package org.panthercode.arctic.core.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.IdentityInfo;
import org.panthercode.arctic.core.helper.version.VersionInfo;
import org.panthercode.arctic.core.processing.ProcessException;
import org.panthercode.arctic.core.processing.ProcessState;
import org.panthercode.arctic.core.processing.modules.helper.TimerOptions;
import org.panthercode.arctic.core.processing.modules.impl.ModuleImpl;
import org.panthercode.arctic.core.processing.modules.impl.Step;
import org.panthercode.arctic.core.processing.modules.impl.Timer;

import java.io.IOException;

/**
 * Created by architect on 02.04.17.
 */
@IdentityInfo(name = "HttpClientModule", group = "Repeater Module")
@VersionInfo(major = 1, minor = 0)
public class HttpClientModule extends ModuleImpl {

    private Timer timer;

    public HttpClientModule() {
        this(new TimerOptions());
    }

    public HttpClientModule(TimerOptions options) {
        this(options, Executor.newInstance());
    }

    public HttpClientModule(TimerOptions options, Executor executor) {
        super();

        ArgumentUtils.checkNotNull(executor, "executor");

        HttpClientStep step = new HttpClientStep(executor);

        this.timer = new Timer(step, options);
    }

    @Override
    public boolean hasResult() {
        return this.timer.hasResult();
    }

    @Override
    public Object result() {
        return this.timer.result();
    }

    @Override
    public boolean start()
            throws ProcessException {
        throw new UnsupportedOperationException("This method is not supported. Use start(Request) or start(Object[]) instead.");
    }

    public boolean start(Request request)
            throws ProcessException {
        ArgumentUtils.checkNotNull(request, "request");

        Object[] args = new Object[]{request};

        return this.start(args);
    }

    @Override
    public synchronized boolean start(Object[] args)
            throws ProcessException {
        ArgumentUtils.checkNotNull(args, "args");

        if (args.length == 0) {
            this.changeState(ProcessState.FAILED);

            throw new ProcessException("Empty arguments array");
        }

        if (!(args[0] instanceof Request)) {
            this.changeState(ProcessState.FAILED);

            throw new ProcessException("Can't find request object in arguments");
        }

        this.reset();

        if (this.changeState(ProcessState.RUNNING)) {
            try {
                this.timer.start(args);

                this.changeState(this.timer.state());

                return this.timer.isSucceeded();
            } catch (Exception e) {
                this.changeState(ProcessState.FAILED);

                throw new ProcessException(e);
            }
        }

        return false;
    }

    @Override
    public boolean stop()
            throws ProcessException {
        return super.reset() && this.timer.stop();
    }

    @Override
    public boolean reset()
            throws ProcessException {
        return super.reset() && this.timer.reset();
    }

    @Override
    public ModuleImpl copy()
            throws UnsupportedOperationException {
        TimerOptions options = new TimerOptions(this.timer.getMaximalDuration(),
                this.timer.getDelayTime(),
                this.timer.isIgnoreExceptions(),
                this.timer.canQuit());

        return new HttpClientModule(options);
    }

    @IdentityInfo(name = "HttpClientStep", group = "Step")
    @VersionInfo(major = 1, minor = 0)
    private class HttpClientStep extends Step {

        private Executor executor;

        private HttpResponse response;

        private HttpResponse result;

        HttpClientStep(Executor executor) {
            super();

            this.executor = executor;
        }

        @Override
        public boolean step(Object[] args)
                throws ProcessException {
            try {
                this.response = this.executor.execute((Request) args[0]).returnResponse();

                if (response.getStatusLine().getStatusCode() < 300) {
                    this.result = this.response;

                    return true;
                } else {
                    throw new ProcessException("Server answered with " + response.getStatusLine().toString());
                }
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public boolean hasResult() {
            return this.result != null;
        }

        @Override
        public Object result() {
            return this.result;
        }

        @Override
        public boolean reset()
                throws ProcessException {
            this.result = null;

            return super.reset();
        }

        @Override
        public Step copy() {
            return new HttpClientStep(this.executor);
        }
    }
}
