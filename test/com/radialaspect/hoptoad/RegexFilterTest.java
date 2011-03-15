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

import java.io.StringWriter;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.junit.Before;
import org.junit.Test;

public class RegexFilterTest {
    private Logger root;
    private RegexFilter filter;
    private StringWriter logged;

    @Before
    public void setUp() {
        root = Logger.getRootLogger();
        root.removeAllAppenders();

        logged = new StringWriter();
        filter = new RegexFilter();

        final Layout layout = new SimpleLayout();

        final Appender appender = new WriterAppender(layout, logged);
        appender.addFilter(filter);

        root.addAppender(appender);
    }

    @Test
    public void testFilter() throws Exception {
        filter.setPattern("test.*");
        filter.setClassName("java.lang.RuntimeException");

        root.error(new RuntimeException("test exception"));

        assertEquals("", logged.getBuffer().toString());
    }

    @Test
    public void testFilterWithNoClassName() throws Exception {
        filter.setPattern("test*");

        root.error(new RuntimeException("test exception"));

        assertEquals("ERROR - java.lang.RuntimeException: test exception\n", logged.getBuffer().toString());
    }

    @Test
    public void testFilterWithNoPattern() throws Exception {
        filter.setClassName("java.lang.RuntimeException");

        root.error(new RuntimeException("test exception"));

        assertEquals("ERROR - java.lang.RuntimeException: test exception\n", logged.getBuffer().toString());
    }
}
