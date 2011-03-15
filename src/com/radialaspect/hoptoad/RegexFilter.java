package com.radialaspect.hoptoad;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import com.radialaspect.hoptoad.util.LoggingEventUtils;

public class RegexFilter extends Filter {

    // class name
    private String className;

    // pattern
    private String pattern;

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int decide(final LoggingEvent event) {
        return isMatch(getThrowableFromEvent(event)) ? DENY : NEUTRAL;
    }

    private Throwable getThrowableFromEvent(final LoggingEvent event) {
        if (event == null) {
            return null;
        }

        return LoggingEventUtils.getThrowableFromEvent(event);
    }

    private boolean isMatch(final Throwable t) {
        return isThrowableComplete(t) && isClassMatch(t) && isPatternMatch(t);
    }

    private boolean isThrowableComplete(final Throwable t) {
        if ((t == null) || (t.getMessage() == null) || (t.getClass() == null) || (t.getClass().getName() == null)) {
            return false;
        }

        return true;
    }

    private boolean isClassMatch(final Throwable t) {
        if (getClassName() == null) {
            return false;
        }

        return getClassName().equalsIgnoreCase(t.getClass().getName());
    }

    private boolean isPatternMatch(final Throwable t) {
        if (getPattern() == null) {
            return false;
        }

        try {
            return Pattern.matches(getPattern(), t.getMessage());
        } catch (final PatternSyntaxException e) {
            return false;
        }
    }
}
