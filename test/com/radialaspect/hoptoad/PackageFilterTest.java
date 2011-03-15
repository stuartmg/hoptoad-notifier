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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.junit.Before;
import org.junit.Test;

public class PackageFilterTest {
    private Logger root;
    private PackageFilter filter;

    @Before
    public void setUp() {
        root = Logger.getRootLogger();
        root.removeAllAppenders();

        filter = new PackageFilter();
    }

    @Test
    public void testPackageNamesWithSinglePackage() throws Exception {
        filter.setPackageNames("com.radialaspect");

        assertEquals(1, filter.getNames().size());
        assertEquals("com.radialaspect", filter.getNames().get(0));
    }

    @Test
    public void testPackageNamesWithMultiplePackages() throws Exception {
        filter.setPackageNames("com.radialaspect, java.lang");

        assertEquals(2, filter.getNames().size());
        assertEquals("com.radialaspect", filter.getNames().get(0));
        assertEquals("java.lang", filter.getNames().get(1));
    }

    @Test
    public void testDecideRejectsError() throws Exception {
        final StringWriter logged = new StringWriter();

        // set up appender
        final Layout layout = new SimpleLayout();
        final Appender appender = new WriterAppender(layout, logged);

        // create PackageFilter
        filter.setPackageNames("java.lang");

        // attach filter to appender
        appender.addFilter(filter);

        root.addAppender(appender);
        root.setLevel(Level.ERROR);

        final Logger logger = Logger.getLogger("test");
        logger.error(new RuntimeException("test exception"));

        assertTrue(logged.getBuffer().length() == 0);
    }

    @Test
    public void testDecideAcceptsError() throws Exception {
        final StringWriter logged = new StringWriter();

        // set up appender
        final Layout layout = new SimpleLayout();
        final Appender appender = new WriterAppender(layout, logged);

        // create PackageFilter
        filter.setPackageNames("java.util");

        // attach match filter to appender
        appender.addFilter(filter);

        root.addAppender(appender);
        root.setLevel(Level.ERROR);

        final Logger logger = Logger.getLogger("test");
        logger.error(new RuntimeException("test exception"));

        System.out.println(logged.getBuffer());
        assertTrue(logged.getBuffer().length() > 0);
    }
}
