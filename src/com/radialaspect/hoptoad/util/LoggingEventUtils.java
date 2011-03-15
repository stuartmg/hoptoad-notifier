package com.radialaspect.hoptoad.util;

import org.apache.log4j.spi.LoggingEvent;

public class LoggingEventUtils {

    public static Throwable getThrowableFromEvent(final LoggingEvent event) {
        if (event == null) {
            return null;
        }

        Throwable t = null;

        if (event.getThrowableInformation() != null) {
            t = event.getThrowableInformation().getThrowable();
        }

        if ((t == null) && (event.getMessage() instanceof Throwable)) {
            t = (Throwable) event.getMessage();
        }

        return t;
    }

}
