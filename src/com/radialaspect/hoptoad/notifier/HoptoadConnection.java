package com.radialaspect.hoptoad.notifier;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HoptoadConnection {

    private String url;
    private boolean secure;

    public HoptoadConnection(final String url, final boolean secure) {
        setUrl(url);
        setSecure(secure);
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(final boolean secure) {
        this.secure = secure;
    }

    public HttpURLConnection connect() throws IOException {
        return (HttpURLConnection) getUrl().openConnection();
    }

    protected URL getUrl() throws MalformedURLException {
        if (url == null) {
            return secure ? new URL(HoptoadNotifier.DEFAULT_HTTPS_URL) : new URL(HoptoadNotifier.DEFAULT_HTTP_URL);
        }

        return new URL(url);
    }
}
