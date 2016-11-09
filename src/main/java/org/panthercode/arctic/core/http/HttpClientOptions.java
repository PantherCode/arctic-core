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
package org.panthercode.arctic.core.http;

import org.apache.http.client.fluent.Request;
import org.panthercode.arctic.core.arguments.ArgumentUtils;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 */
public class HttpClientOptions {

    /**
     *
     */
    private URI host = null;

    /**
     *
     */
    private URI proxy = null;

    /**
     *
     */
    private int socketTimeoutInMillis = 1000;

    /**
     *
     */
    private int connectionTimeoutInMillis = 1000;

    /**
     *
     */
    private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * @param host
     * @param proxy
     * @param socketTimeoutInMillis
     * @param connectionTimeoutInMillis
     */
    private HttpClientOptions(URI host,
                              URI proxy,
                              int socketTimeoutInMillis,
                              int connectionTimeoutInMillis) {
        this.setSocketTimeout(socketTimeoutInMillis);
        this.setConnectionTimeout(connectionTimeoutInMillis);
        this.setProxy(proxy);
        this.setHost(host);
    }

    /**
     * @return
     */
    public static HttpClientOptionsBuilder create() {
        return new HttpClientOptionsBuilder();
    }

    /**
     * @return
     */
    public URI getHost() {
        return this.host;
    }

    /**
     * @param host
     */
    public void setHost(URI host) {
        ArgumentUtils.assertNotNull(host, "host");

        this.host = host;
    }

    /**
     * @param host
     */
    public void setHost(String host) {
        this.setHost(URI.create(host));
    }

    /**
     * @return
     */
    public URI getProxy() {
        return this.proxy;
    }

    /**
     * @param proxy
     */
    public void setProxy(URI proxy) {
        this.proxy = proxy;
    }

    /**
     * @param proxy
     */
    public void setProxy(String proxy) {
        this.setProxy(URI.create(proxy));
    }

    /**
     * @return
     */
    public int getSocketTimeout() {
        return this.socketTimeoutInMillis;
    }

    /**
     * @param timeoutInMillis
     */
    public void setSocketTimeout(int timeoutInMillis) {
        ArgumentUtils.assertGreaterOrEqualsZero(timeoutInMillis, "timeout");

        this.socketTimeoutInMillis = timeoutInMillis;
    }

    /**
     * @param duration
     * @param unit
     */
    public void setSocketTimeout(int duration, TimeUnit unit) {
        this.setSocketTimeout((int) this.timeUnit.convert(duration, unit));
    }

    /**
     * @return
     */
    public int getConnectionTimeout() {
        return this.connectionTimeoutInMillis;
    }

    /**
     * @param timeoutInMillis
     */
    public void setConnectionTimeout(int timeoutInMillis) {
        ArgumentUtils.assertGreaterOrEqualsZero(timeoutInMillis, "timeout");

        this.socketTimeoutInMillis = timeoutInMillis;
    }

    /**
     * @param duration
     * @param unit
     */
    public void setConnectionTimeoutInMillis(int duration, TimeUnit unit) {
        this.setSocketTimeout((int) this.timeUnit.convert(duration, unit));
    }

    /**
     * @return
     */
    public Request post() {
        return this.post("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request post(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.post(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request post(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Post(this.host.resolve(uri)));
    }

    /**
     * @return
     */
    public Request get() {
        return this.get("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request get(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.get(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request get(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Get(this.host.resolve(uri)));
    }

    /**
     * @return
     */
    public Request options() {
        return this.options("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request options(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.options(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request options(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Options(this.host.resolve(uri)));
    }

    /**
     * @return
     */
    public Request trace() {
        return this.trace("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request trace(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.trace(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request trace(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Trace(this.host.resolve(uri)));
    }

    /**
     * @return
     */
    public Request put() {
        return this.put("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request put(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.put(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request put(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Put(this.host.resolve(uri)));
    }

    /**
     * @return
     */
    public Request head() {
        return this.head("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request head(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.head(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request head(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Head(this.host.resolve(uri)));
    }

    /**
     * @return
     */
    public Request patch() {
        return this.patch("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request patch(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.patch(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request patch(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Patch(this.host.resolve(uri)));
    }

    /**
     * @return
     */
    public Request delete() {
        return this.delete("/");
    }

    /**
     * @param uri
     * @return
     */
    public Request delete(String uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.delete(URI.create(uri));
    }

    /**
     * @param uri
     * @return
     */
    public Request delete(URI uri) {
        ArgumentUtils.assertNotNull(uri, "uri");

        return this.request(Request.Delete(this.host.resolve(uri)));
    }

    /**
     * @param request
     * @return
     */
    private Request request(Request request) {
        request = request.socketTimeout(this.socketTimeoutInMillis)
                .connectTimeout(this.connectionTimeoutInMillis);

        if (this.proxy != null) {
            request = request.viaProxy(this.proxy.toString());
        }

        return request;
    }

    /**
     *
     */
    public static class HttpClientOptionsBuilder {

        /**
         *
         */
        private URI host = null;

        /**
         *
         */
        private URI proxy = null;

        /**
         *
         */
        private int socketTimeoutInMillis = 1000;

        /**
         *
         */
        private int connectionTimeoutInMillis = 1000;

        /**
         *
         */
        private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        /**
         *
         */
        public HttpClientOptionsBuilder() {
        }

        /**
         * @param host
         * @return
         */
        public HttpClientOptionsBuilder setHost(String host) {
            return this.setHost(URI.create(host));
        }

        /**
         * @param host
         * @return
         */
        public HttpClientOptionsBuilder setHost(URI host) {
            this.host = host;

            return this;
        }

        /**
         * @param proxy
         * @return
         */
        public HttpClientOptionsBuilder setProxy(String proxy) {
            return this.setProxy(URI.create(proxy));
        }

        /**
         * @param proxy
         * @return
         */
        public HttpClientOptionsBuilder setProxy(URI proxy) {
            this.proxy = proxy;

            return this;
        }

        /**
         * @param timeoutInMillis
         * @return
         */
        public HttpClientOptionsBuilder setConnectionTimeout(int timeoutInMillis) {
            this.connectionTimeoutInMillis = timeoutInMillis;

            return this;
        }

        /**
         * @param duration
         * @param unit
         * @return
         */
        public HttpClientOptionsBuilder setConnectionTimeout(int duration, TimeUnit unit) {
            this.connectionTimeoutInMillis = (int) this.timeUnit.convert((long) duration, unit);

            return this;
        }

        /**
         * @param timeoutInMillis
         * @return
         */
        public HttpClientOptionsBuilder setSocketTimeout(int timeoutInMillis) {
            this.socketTimeoutInMillis = timeoutInMillis;

            return this;
        }

        /**
         * @param duration
         * @param unit
         * @return
         */
        public HttpClientOptionsBuilder setSocketTimeout(int duration, TimeUnit unit) {
            this.socketTimeoutInMillis = (int) timeUnit.convert((long) duration, unit);

            return this;
        }

        /**
         * @return
         */
        public int getSocketTimeout() {
            return this.socketTimeoutInMillis;
        }

        /**
         * @return
         */
        public int getConnectionTimeout() {
            return this.connectionTimeoutInMillis;
        }

        /**
         * @return
         */
        public URI getHost() {
            return this.host;
        }

        /**
         * @return
         */
        public URI getProxy() {
            return this.proxy;
        }

        /**
         * @return
         */
        public HttpClientOptions build() {
            return new HttpClientOptions(this.host,
                    this.proxy,
                    this.socketTimeoutInMillis,
                    this.connectionTimeoutInMillis);
        }
    }
}
