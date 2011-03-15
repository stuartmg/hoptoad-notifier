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
