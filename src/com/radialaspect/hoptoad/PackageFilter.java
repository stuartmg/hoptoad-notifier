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
