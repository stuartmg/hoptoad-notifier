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
