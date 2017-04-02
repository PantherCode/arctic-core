package org.panthercode.arctic.core.http;

import org.panthercode.arctic.core.processing.ProcessException;
import org.panthercode.arctic.core.processing.modules.helper.TimerOptions;
import org.panthercode.arctic.core.processing.modules.impl.ModuleImpl;
import org.panthercode.arctic.core.processing.modules.impl.Step;

/**
 * Created by architect on 02.04.17.
 */
public class HttpClientModule extends ModuleImpl {

    public HttpClientModule(HttpClient client, TimerOptions options) {

    }

    @Override
    public boolean hasResult() {
        return false;
    }

    @Override
    public Object result() {
        return null;
    }

    @Override
    public boolean start(Object[] args) throws ProcessException {
        return false;
    }

    @Override
    public boolean stop() throws ProcessException {
        return false;
    }

    @Override
    public boolean reset() throws ProcessException {
        return false;
    }

    @Override
    public ModuleImpl copy() throws UnsupportedOperationException {
        return null;
    }

    private class HttpClientStep extends Step {

        @Override
        public boolean step(Object[] args) throws ProcessException {
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

        @Override
        public boolean stop() throws ProcessException {
            return false;
        }

        @Override
        public boolean reset() throws ProcessException {
            return false;
        }

        @Override
        public Step copy() {
            return new HttpClientStep();
        }
    }
}
