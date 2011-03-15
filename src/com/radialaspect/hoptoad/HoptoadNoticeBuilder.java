package com.radialaspect.hoptoad;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.radialaspect.hoptoad.schema.Notice;
import com.radialaspect.hoptoad.schema.Request;
import com.radialaspect.hoptoad.schema.ServerEnvironment;

public class HoptoadNoticeBuilder {
    private String apiKey;
    private String projectRoot;
    private String environment;
    private Throwable error;
    private String url;
    private final Map<String, String> params = new TreeMap<String, String>();
    private final Map<String, String> session = new TreeMap<String, String>();
    private final Map<String, String> headers = new TreeMap<String, String>();

    public HoptoadNoticeBuilder() {
    }

    public HoptoadNoticeBuilder apiKey(final String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public HoptoadNoticeBuilder projectRoot(final String projectRoot) {
        this.projectRoot = projectRoot;
        return this;
    }

    public HoptoadNoticeBuilder environment(final String environment) {
        this.environment = environment;
        return this;
    }

    public HoptoadNoticeBuilder error(final Throwable error) {
        this.error = error;
        return this;
    }

    public HoptoadNoticeBuilder url(final String url) {
        this.url = url;
        return this;
    }

    public HoptoadNoticeBuilder request(final HttpServletRequest request) {
        if (request != null) {
            projectRoot(request.getContextPath());
            url(request.getRequestURI());

            buildParams(request);
            buildSession(request);
            buildHeaders(request);
        }

        return this;
    }

    public Notice build() {
        try {
            return new Notice(getApiKey(), getError(), getServerEnvironment(), buildRequest());
        } catch (final Exception e) {
            return null;
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getProjectRoot() {
        return projectRoot;
    }

    public String getEnvironment() {
        return environment;
    }

    public Throwable getError() {
        return error;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getSession() {
        return session;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    private Map<String, String> getSystemProperties() {
        final Map<String, String> props = new TreeMap<String, String>();

        for (final Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            if ((getCleanValue(entry.getKey()) == null) || (getCleanValue(entry.getValue()) == null)) {
                continue;
            }

            props.put(entry.getKey().toString(), entry.getValue().toString());
        }

        return props;
    }

    private ServerEnvironment getServerEnvironment() {
        return new ServerEnvironment(getProjectRoot(), getEnvironment());
    }

    private Request buildRequest() {
        if (getUrl() == null) {
            return null;
        }

        final Request request = new Request(getUrl(), null, null);

        request.addParams(getParams());
        request.addSessionParams(getSession());
        request.addCgiData(getHeaders());
        request.addCgiData(getSystemProperties());

        return request;
    }

    @SuppressWarnings("unchecked")
    private void buildParams(final HttpServletRequest request) {
        for (final Map.Entry<String, String[]> entry : (Set<Map.Entry<String, String[]>>) request.getParameterMap().entrySet()) {
            params.put(entry.getKey(), joinValues(entry.getValue()));
        }
    }

    @SuppressWarnings("unchecked")
    private void buildSession(final HttpServletRequest request) {
        final HttpSession httpSession = request.getSession();
        final Enumeration<String> e = httpSession.getAttributeNames();

        while (e.hasMoreElements()) {
            final String name = e.nextElement();

            if (name.toLowerCase().contains("password")) {
                continue;
            }

            final Object value = httpSession.getAttribute(name);

            if (value == null) {
                session.put(name, "NULL");
            } else {
                session.put(name, value.toString());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void buildHeaders(final HttpServletRequest request) {
        final Enumeration<String> e = request.getHeaderNames();

        while (e.hasMoreElements()) {
            final String name = e.nextElement();

            headers.put(name, request.getHeader(name));
        }
    }

    private String joinValues(final String[] values) {
        final StringBuilder sb = new StringBuilder(2048);

        for (final String val : values) {
            if (sb.length() > 0) {
                sb.append(", ");
            }

            sb.append(val);
        }

        return sb.toString();
    }

    private String getCleanValue(final Object value) {
        if ((value == null) || "".equals(value.toString().trim())) {
            return null;
        }

        return value.toString().trim();
    }
}
