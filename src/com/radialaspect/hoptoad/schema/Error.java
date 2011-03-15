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

@XStreamAlias("error")
public class Error {
    // name of class in which error occurred
    @XStreamAlias("class")
    private String className;

    // error message
    private String message;

    // stack trace
    private Backtrace backtrace;

    public Error(final Throwable throwable) {
        String message = null;

        if (throwable.getMessage() != null) {
            message = throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
        }

        setClassName(throwable.getClass().getName());
        setBacktrace(new Backtrace(throwable));
        setMessage(message);
    }

    public Error(final String className, final String message, final Backtrace backtrace) {
        setClassName(className);
        setBacktrace(backtrace);
        setMessage(message);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        if ((className == null) || "".equals(className.trim())) {
            throw new IllegalArgumentException("class name must not be empty");
        }

        this.className = className;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        if ((message == null) || "".equals(message.trim())) {
            this.message = getDefaultMessage();
        } else {
            this.message = message.trim();
        }
    }

    public Backtrace getBacktrace() {
        return backtrace;
    }

    public void setBacktrace(final Backtrace backtrace) {
        this.backtrace = backtrace;
    }

    private String getDefaultMessage() {
        if (getBacktrace() == null) {
            return getClassName();
        }

        final StringBuilder message = new StringBuilder(255);

        message.append(getSimpleClassName());
        message.append(" at line ");
        message.append(getLineNumberOfError());

        return message.toString();
    }

    private String getSimpleClassName() {
        if (getClassName() == null) {
            return null;
        }

        final int index = getClassName().lastIndexOf('.');
        if (index < 0) {
            return getClassName();
        }

        return getClassName().substring(index + 1);
    }

    private int getLineNumberOfError() {
        if (getBacktrace() == null) {
            return 0;
        }

        if (getBacktrace().getLines().isEmpty()) {
            return 0;
        }

        return getBacktrace().getLines().get(0).getLineNumber();
    }
}
