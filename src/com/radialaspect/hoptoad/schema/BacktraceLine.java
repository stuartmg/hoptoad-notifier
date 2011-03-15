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
