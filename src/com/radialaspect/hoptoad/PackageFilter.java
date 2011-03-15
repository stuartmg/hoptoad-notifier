package com.radialaspect.hoptoad;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import com.radialaspect.hoptoad.util.LoggingEventUtils;

public class PackageFilter extends Filter {

    // package names
    private List<String> names = new ArrayList<String>();

    public void setPackageNames(final String packageNames) {
        if (packageNames == null) {
            return;
        }

        for (final String part : packageNames.split(",")) {
            addPackageName(part);
        }
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(final List<String> names) {
        this.names = (names == null) ? new ArrayList<String>() : names;
    }

    @Override
    public int decide(final LoggingEvent event) {
        final Throwable t = getThrowableFromEvent(event);
        if ((t == null) || (t.getClass().getName() == null)) {
            return NEUTRAL;
        }

        return isPackageMatch(t.getClass().getName()) ? DENY : NEUTRAL;
    }

    private Throwable getThrowableFromEvent(final LoggingEvent event) {
        if (event == null) {
            return null;
        }

        return LoggingEventUtils.getThrowableFromEvent(event);
    }

    private boolean isPackageMatch(final String className) {
        if (className == null) {
            return false;
        }

        for (final String packageName : getNames()) {
            if (className.startsWith(packageName)) {
                return true;
            }
        }

        return false;
    }

    private void addPackageName(final String name) {
        if ((name == null) || "".equals(name.trim())) {
            return;
        }

        names.add(name.trim());
    }
}
