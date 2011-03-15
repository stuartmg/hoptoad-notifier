package com.radialaspect.hoptoad;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

import com.radialaspect.hoptoad.notifier.DefaultHoptoadNotifier;
import com.radialaspect.hoptoad.notifier.HoptoadException;
import com.radialaspect.hoptoad.notifier.HoptoadNotifier;
import com.radialaspect.hoptoad.notifier.SSLNotSupportedException;
import com.radialaspect.hoptoad.schema.Notice;
import com.radialaspect.hoptoad.util.LoggingEventUtils;
import com.thoughtworks.xstream.XStream;

public class HoptoadAppender extends AppenderSkeleton {
    private HoptoadNotifier notifier;

    private String apiKey;
    private String environment;
    private String url;
    private boolean ssl;
    private boolean enabled;

    public HoptoadAppender() {
        setThreshold(Level.ERROR);
    }

    public HoptoadNotifier getNotifier() {
        if (notifier == null) {
            notifier = new DefaultHoptoadNotifier(getUrl(), isSsl());
        }

        return notifier;
    }

    public void setNotifier(final HoptoadNotifier notifier) {
        this.notifier = notifier;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;

        // disable if api key is null
        setEnabled(apiKey != null);
    }

    public void setApi_key(final String apiKey) {
        setApiKey(apiKey);
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(final boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    protected void append(final LoggingEvent event) {
        if (!isEnabled()) {
            return;
        }

        final Throwable throwable = LoggingEventUtils.getThrowableFromEvent(event);

        if (throwable == null) {
            errorHandler.error("no exception in logging event - skipping");
            return;
        }

        try {
            final XStream stream = new XStream();
            stream.autodetectAnnotations(true);

            final Notice notice = getNotice(throwable);
            final String response = getNotifier().notify(notice);

            Logger.getRootLogger().info("response from notifier: " + response);
        } catch (final SSLNotSupportedException e) {
            // set ssl to false and try again
            if (isSsl()) {
                setSsl(false);
                getNotifier().setSecure(false);
                append(event);
            } else {
                errorHandler.error("error generating notice", e, ErrorCode.GENERIC_FAILURE, event);
            }
        } catch (final HoptoadException e) {
            Logger.getRootLogger().error("Error generating notice: " + e.getMessage() + " - response: " + e.getResponse());
            errorHandler.error("error generating notice", e, ErrorCode.GENERIC_FAILURE, event);
        } catch (final Exception e) {
            Logger.getRootLogger().error("Error generating notice: " + e.getMessage());
            errorHandler.error("error generating notice", e, ErrorCode.GENERIC_FAILURE, event);
        }
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    public void close() {
        setEnabled(false);
    }

    private Notice getNotice(final Throwable throwable) {
        if (throwable == null) {
            return null;
        }

        return getBuilder().error(throwable).request((HttpServletRequest) MDC.get("request")).build();
    }

    private HoptoadNoticeBuilder getBuilder() {
        return new HoptoadNoticeBuilder().apiKey(getApiKey()).environment(getEnvironment());
    }
}
