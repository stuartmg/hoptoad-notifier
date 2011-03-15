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
