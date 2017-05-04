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
import org.panthercode.arctic.core.web.Destination;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * <tt>HttpClient</tt> holds a set of http parameters to establish easily connections to a host machine (also via
 * proxy if it's needed). The class also offers a set functions to create http packages.
 * <p>
 * Often you would like to connect with different paths or resource at same host. For this case you can set a default
 * host and call desired method with the specific path the resource is stored at the host machine
 * <pre>
 * {@code
 * HttpClient client = ...
 *                     .setHost("http://www.example.org")
 *                     .build();
 *
 * Response response1 = options.get().execute(); //GET request to http://www.example.org
 *
 * Response response2 = options.get("/path/to/resource").execute(); //GET request to http://www.example.org/path/to/resource
 *
 * Response response3 = options.get("http://www.other-site.org").execute(); //GET request http://www.other-site.org
 * }
 * </pre>
 * For process the uri parameter of http method functions the <tt>resolve()</tt> method of <tt>URI</tt> class is used.
 *
 * @author PantherCode
 * @since 1.0
 */
public class HttpClient {

    /**
     * default address of host machine
     */
    private URI host = null;

    /**
     * default address of used proxy
     */
    private URI proxy = null;

    /**
     * timeout for responding of host
     */
    private int socketTimeoutInMillis = 1000;

    /**
     * timeout for connecting to host machine
     */
    private int connectionTimeoutInMillis = 1000;

    /**
     * used unit for timeouts
     */
    private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * Constructor
     *
     * @param host                      address of host machine
     * @param proxy                     address of proxy machine
     * @param socketTimeoutInMillis     timeout for responding
     * @param connectionTimeoutInMillis timeout to connect
     */
    private HttpClient(URI host,
                       URI proxy,
                       int socketTimeoutInMillis,
                       int connectionTimeoutInMillis) {
        this.setSocketTimeout(socketTimeoutInMillis);
        this.setConnectionTimeout(connectionTimeoutInMillis);
        this.setProxy(proxy);
        this.setHost(host);
    }

    /**
     * Create a new instance of a <tt>HttpOptionsBuilder</tt> class.
     *
     * @return Returns a new instance of builder class.
     */
    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    public HttpClient copy() {
        return new HttpClient(this.host,
                this.proxy,
                this.socketTimeoutInMillis,
                this.connectionTimeoutInMillis);
    }

    /**
     * Returns the default address of the host machine.
     *
     * @return Returns the default address of the host machine.
     */
    public URI getHost() {
        return this.host;
    }

    /**
     * Sets the default address of host machine.
     *
     * @param host address of host machine
     * @throws NullPointerException Is thrown if the value of parameter is null.
     */
    public void setHost(URI host)
            throws NullPointerException {
        ArgumentUtils.checkNotNull(host, "host");

        this.host = host;
    }

    /**
     * Sets the default address of host machine.
     *
     * @param host address of host machine
     * @throws NullPointerException Is thrown if the value of parameter is null.
     */
    public void setHost(String host)
            throws NullPointerException {
        this.setHost(URI.create(host));
    }

    /**
     * Returns the default address of the proxy.
     *
     * @return Returns the default address of the host machine.
     */
    public URI getProxy() {
        return this.proxy;
    }

    /**
     * Sets the default address of proxy. If you don't need a proxy, set it to <tt>null</tt>.
     *
     * @param proxy address of proxy
     */
    public void setProxy(URI proxy) {
        this.proxy = proxy;
    }

    /**
     * Sets the default address of proxy. If you don't need a proxy, set it to <tt>null</tt>.
     *
     * @param proxy address of porxy
     */
    public void setProxy(String proxy) {
        this.setProxy(URI.create(proxy));
    }

    /**
     * Returns the timeout for waiting to a server response (in millis).
     *
     * @return Returns the timeout for waiting to a server response.
     */
    public int getSocketTimeout() {
        return this.socketTimeoutInMillis;
    }


    //TODO: implement Getter with Timeunit parameter

    /**
     * Sets the timeout for waiting for a server response.
     *
     * @param timeoutInMillis value of timeout in millis
     * @throws IllegalArgumentException Is thrown if the value of timeout is less than zero.
     */
    public void setSocketTimeout(int timeoutInMillis)
            throws IllegalArgumentException {
        ArgumentUtils.checkGreaterOrEqualsZero(timeoutInMillis, "timeout");

        this.socketTimeoutInMillis = timeoutInMillis;
    }

    /**
     * Sets the timeout for waiting for a server response.
     *
     * @param duration duration of timeout
     * @param unit     time unit of duration
     * @throws IllegalArgumentException Is thrown if the value of parameter is less than zero.
     */
    public void setSocketTimeout(int duration, TimeUnit unit)
            throws IllegalArgumentException {
        this.setSocketTimeout((int) this.timeUnit.convert(duration, unit));
    }

    /**
     * Returns the timeout for waiting to connect to the server (in millis).
     *
     * @return Returns the timeout for waiting to connect to the server.
     */
    public int getConnectionTimeout() {
        return this.connectionTimeoutInMillis;
    }

    //TODO: implement Getter with Timeunit parameter

    /**
     * Sets the timeout for waiting for connection to the server.
     *
     * @param timeoutInMillis value of timeout in millis
     * @throws IllegalArgumentException Is thrown if the value of timeout is less than zero.
     */
    public void setConnectionTimeout(int timeoutInMillis)
            throws IllegalArgumentException {
        ArgumentUtils.checkGreaterOrEqualsZero(timeoutInMillis, "timeout");

        this.socketTimeoutInMillis = timeoutInMillis;
    }

    /**
     * Sets the timeout for waiting for connection to the server.
     *
     * @param duration duration of timeout
     * @param unit     time unit of duration
     * @throws IllegalArgumentException Is thrown if the value of parameter is less than zero.
     */
    public void setConnectionTimeoutInMillis(int duration, TimeUnit unit) {
        this.setSocketTimeout((int) this.timeUnit.convert(duration, unit));
    }

    /**
     * Returns a new http post request.
     *
     * @return Returns a new http post request.
     */
    public Request post() {
        return this.post("/");
    }

    /**
     * Returns a new http post request.
     *
     * @param uri path to specify the target
     * @return Returns a new http post request.
     */
    public Request post(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.post(URI.create(uri));
    }

    /**
     * Returns a new http post request.
     *
     * @param uri path to specify the target
     * @return Returns a new http post request.
     */
    public Request post(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Post(this.host.resolve(uri)));
    }

    /**
     * Returns a new http get request.
     *
     * @return Returns a new http get request.
     */
    public Request get() {
        return this.get("/");
    }

    /**
     * Returns a new http get request.
     *
     * @param uri path to specify the target
     * @return Returns a new http get request.
     */
    public Request get(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.get(URI.create(uri));
    }

    /**
     * Returns a new http get request.
     *
     * @param uri path to specify the target
     * @return Returns a new http get request.
     */
    public Request get(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Get(this.host.resolve(uri)));
    }

    /**
     * Returns a new http options request.
     *
     * @return Returns a new http options request.
     */
    public Request options() {
        return this.options("/");
    }

    /**
     * Returns a new http options request.
     *
     * @param uri path to specify the target
     * @return Returns a new http options request.
     */
    public Request options(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.options(URI.create(uri));
    }

    /**
     * Returns a new http options request.
     *
     * @param uri path to specify the target
     * @return Returns a new http options request.
     */
    public Request options(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Options(this.host.resolve(uri)));
    }

    /**
     * Returns a new http trace request.
     *
     * @return Returns a new http trace request.
     */
    public Request trace() {
        return this.trace("/");
    }

    /**
     * Returns a new http trace request.
     *
     * @param uri path to specify the target
     * @return Returns a new http trace request.
     */
    public Request trace(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.trace(URI.create(uri));
    }

    /**
     * Returns a new http trace request.
     *
     * @param uri path to specify the target
     * @return Returns a new http trace request.
     */
    public Request trace(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Trace(this.host.resolve(uri)));
    }

    /**
     * Returns a new http put request.
     *
     * @return Returns a new http put request.
     */
    public Request put() {
        return this.put("/");
    }

    /**
     * Returns a new http put request.
     *
     * @param uri path to specify the target
     * @return Returns a new http put request.
     */
    public Request put(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.put(URI.create(uri));
    }

    /**
     * Returns a new http put request.
     *
     * @param uri path to specify the target
     * @return Returns a new http put request.
     */
    public Request put(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Put(this.host.resolve(uri)));
    }

    /**
     * Returns a new http head request.
     *
     * @return Returns a new http head request.
     */
    public Request head() {
        return this.head("/");
    }

    /**
     * Returns a new http head request.
     *
     * @param uri path to specify the target
     * @return Returns a new http head request.
     */
    public Request head(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.head(URI.create(uri));
    }

    /**
     * Returns a new http head request.
     *
     * @param uri path to specify the target
     * @return Returns a new http head request.
     */
    public Request head(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Head(this.host.resolve(uri)));
    }

    /**
     * Returns a new http patch request.
     *
     * @return Returns a new http patch request.
     */
    public Request patch() {
        return this.patch("/");
    }

    /**
     * Returns a new http patch request.
     *
     * @param uri path to specify the target
     * @return Returns a new http patch request.
     */
    public Request patch(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.patch(URI.create(uri));
    }

    /**
     * Returns a new http patch request.
     *
     * @param uri path to specify the target
     * @return Returns a new http patch request.
     */
    public Request patch(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Patch(this.host.resolve(uri)));
    }

    /**
     * Returns a new http delete request.
     *
     * @return Returns a new http delete request.
     */
    public Request delete() {
        return this.delete("/");
    }

    /**
     * Returns a new http delete request.
     *
     * @param uri path to specify the target
     * @return Returns a new http delete request.
     */
    public Request delete(String uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.delete(URI.create(uri));
    }

    /**
     * Returns a new http delete request.
     *
     * @param uri path to specify the target
     * @return Returns a new http delete request.
     */
    public Request delete(URI uri) {
        ArgumentUtils.checkNotNull(uri, "uri");

        return this.request(Request.Delete(this.host.resolve(uri)));
    }

    /**
     * Returns a new http request with default parameters.
     *
     * @return Returns a new http request with default parameters.
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
     * Builder class to create instances of <tt>HttpClientOptions</tt> class.
     */
    public static class HttpClientBuilder {

        /**
         * default address of host machine
         */
        private URI host = null;

        /**
         * default address of used proxy
         */
        private URI proxy = null;

        /**
         * timeout for responding of host
         */
        private int socketTimeoutInMillis = 1000;

        /**
         * timeout for connecting to host machine
         */
        private int connectionTimeoutInMillis = 1000;

        /**
         * used unit for timeouts
         */
        private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        /**
         * Standard Constructor
         */
        public HttpClientBuilder() {
        }

        public HttpClientBuilder setDestination(Destination destination) {
            if (destination != null) {
                this.host = destination.toURI();

                if (destination.hasProxy()) {
                    this.proxy = destination.getProxy().toURI();
                }
            }

            return this;
        }

        /**
         * Sets the default address of host machine.
         *
         * @param host address of host machine
         * @return Returns the instance of builder with changed host.
         */
        public HttpClientBuilder setHost(String host) {
            return this.setHost(URI.create(host));
        }

        /**
         * Sets the default address of host machine.
         *
         * @param host address of host machine
         * @return Returns the instance of builder with changed host.
         */
        public HttpClientBuilder setHost(URI host) {
            this.host = host;

            return this;
        }

        /**
         * Sets the default address of proxy.
         *
         * @param proxy address  of proxy
         * @return RReturns the instance of builder with changed proxy.
         */
        public HttpClientBuilder setProxy(String proxy) {
            return this.setProxy(URI.create(proxy));
        }

        /**
         * Sets the default address of proxy.
         *
         * @param proxy address of proxy
         * @return Returns the instance of builder with changed proxy.
         */
        public HttpClientBuilder setProxy(URI proxy) {
            this.proxy = proxy;

            return this;
        }

        /**
         * Sets the timeout for waiting for connection to the server.
         *
         * @param timeoutInMillis duration of timeout in millis
         * @return Returns the instance of builder with changed connection timeout.
         */
        public HttpClientBuilder setConnectionTimeout(int timeoutInMillis) {
            this.connectionTimeoutInMillis = timeoutInMillis;

            return this;
        }

        /**
         * Sets the timeout for waiting for connection to the server.
         *
         * @param duration duration of timeout
         * @param unit     time unit of duration
         * @return Returns the instance of builder with change connection timeout.
         */
        public HttpClientBuilder setConnectionTimeout(int duration, TimeUnit unit) {
            this.connectionTimeoutInMillis = (int) this.timeUnit.convert((long) duration, unit);

            return this;
        }

        /**
         * Sets the timeout for waiting for a server response.
         *
         * @param timeoutInMillis duration of timeout in millis
         * @return Returns the instance of builder with changed socket timeout.
         */
        public HttpClientBuilder setSocketTimeout(int timeoutInMillis) {
            this.socketTimeoutInMillis = timeoutInMillis;

            return this;
        }

        /**
         * Sets the timeout for waiting for a server response.
         *
         * @param duration duration of timeout
         * @param unit     time unit of duration
         * @return Returns the instance of builder with changed socket timeout.
         */
        public HttpClientBuilder setSocketTimeout(int duration, TimeUnit unit) {
            this.socketTimeoutInMillis = (int) timeUnit.convert((long) duration, unit);

            return this;
        }

        /**
         * Returns the timeout for waiting to a server response (in millis).
         *
         * @return Returns the timeout for waiting to a server response.
         */
        public int getSocketTimeout() {
            return this.socketTimeoutInMillis;
        }

        //TODO: implement Getter with Timeunit parameter

        /**
         * Returns the timeout for waiting to connect to the server (in millis).
         *
         * @return Returns the timeout for waiting to connect to the server (in millis).
         */
        public int getConnectionTimeout() {
            return this.connectionTimeoutInMillis;
        }

        //TODO: implement Getter with Timeunit parameter

        /**
         * Returns the default of the host machine.
         *
         * @return Returns the address of the host machine.
         */
        public URI getHost() {
            return this.host;
        }

        /**
         * Returns the default address of the proxy.
         *
         * @return Returns the default address of the proxy.
         */
        public URI getProxy() {
            return this.proxy;
        }

        /**
         * Returns a new instance of <tt>HttpClientOptions</tt> class.
         *
         * @return Returns a new instance of <tt>HttpClientOptions</tt> class.
         */
        public HttpClient build() {
            return new HttpClient(this.host,
                    this.proxy,
                    this.socketTimeoutInMillis,
                    this.connectionTimeoutInMillis);
        }
    }
}
