/*
 * Copyright (C) 2011 by Stuart Garner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.radialaspect.hoptoad.schema;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("notice")
public class Notice {
    public static final String API_VERSION = "2.0";

    @XStreamAsAttribute
    private final String version = API_VERSION;

    @XStreamAlias("api-key")
    private String apiKey;

    // notifier
    private final Notifier notifier = Notifier.getInstance();

    // error
    private Error error;

    // request
    private Request request;

    // server environment
    @XStreamAlias("server-environment")
    private ServerEnvironment serverEnvironment;

    public Notice(final String apiKey, final Throwable throwable, final ServerEnvironment environment) {
        this(apiKey, throwable, environment, null);
    }

    public Notice(final String apiKey, final Throwable throwable, final ServerEnvironment environment, final Request request) {
        setApiKey(apiKey);
        setError(throwable);
        setServerEnvironment(environment);
        setRequest(request);
    }

    public final String getVersion() {
        return version;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public Error getError() {
        return error;
    }

    public void setError(final Error error) {
        this.error = error;
    }

    public void setError(final Throwable throwable) {
        setError(new Error(throwable));
    }

    public ServerEnvironment getServerEnvironment() {
        return serverEnvironment;
    }

    public void setServerEnvironment(final ServerEnvironment serverEnvironment) {
        this.serverEnvironment = serverEnvironment;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(final Request request) {
        this.request = request;
    }
}
