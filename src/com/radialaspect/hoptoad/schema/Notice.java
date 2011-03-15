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
