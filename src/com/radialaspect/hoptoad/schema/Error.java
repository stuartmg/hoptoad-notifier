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
