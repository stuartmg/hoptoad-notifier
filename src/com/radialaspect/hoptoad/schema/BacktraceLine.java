package com.radialaspect.hoptoad.schema;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("line")
public class BacktraceLine {
    // filename
    @XStreamAsAttribute
    private String file;

    // line number
    @XStreamAlias("number")
    @XStreamAsAttribute
    private int lineNumber;

    // method name
    @XStreamAsAttribute
    private String method;

    public BacktraceLine(final String file, final int lineNumber) {
        this(file, lineNumber, null);
    }

    public BacktraceLine(final String file, final int lineNumber, final String method) {
        setFile(file);
        setLineNumber(lineNumber);
        setMethod(method);
    }

    public String getFile() {
        return file;
    }

    public void setFile(final String file) {
        if ((file == null) || "".equals(file.trim())) {
            throw new IllegalArgumentException("file must not be empty");
        }

        this.file = file.trim();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        if ((method == null) || "".equals(method.trim())) {
            this.method = null;
        } else {
            this.method = method.trim();
        }
    }
}
