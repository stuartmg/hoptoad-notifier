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

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("backtrace")
public class Backtrace {
    @XStreamImplicit
    private final List<BacktraceLine> lines = new ArrayList<BacktraceLine>();

    public Backtrace(final Throwable throwable) {
        populateLines(throwable);
    }

    public List<BacktraceLine> getLines() {
        return lines;
    }

    private void populateLines(final Throwable throwable) {
        if (throwable == null) {
            return;
        }

        BacktraceLine line = null;

        for (final StackTraceElement element : throwable.getStackTrace()) {
            line = getBacktraceLine(element);

            if (line != null) {
                lines.add(line);
            }
        }

        populateLines(throwable.getCause());
    }

    private BacktraceLine getBacktraceLine(final StackTraceElement line) {
        try {
            return new BacktraceLine(getFilename(line.getClassName()), line.getLineNumber(), line.getMethodName());
        } catch (final Exception e) {
            return null;
        }
    }

    private String getFilename(final String className) {
        if (className == null) {
            return "";
        }

        String filename = className;
        final int index = filename.indexOf('$');

        if (index >= 0) {
            filename = filename.substring(0, index);
        }

        return filename + ".java";
    }
}
