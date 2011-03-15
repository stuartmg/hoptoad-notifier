package com.radialaspect.hoptoad.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ErrorTest {
    private Error error;
    private Throwable throwable;

    @Before
    public void setUp() {
        throwable = new RuntimeException("test exception");
        error = new Error(throwable);
    }

    @Test
    public void testConstructorWithMessage() throws Exception {
        assertEquals("java.lang.RuntimeException", error.getClassName());
        assertEquals("RuntimeException: test exception", error.getMessage());
        assertNotNull(error.getBacktrace());
    }

    @Test
    public void testConstructorWithEmptyMessage() throws Exception {
        throwable = new RuntimeException();
        error = new Error(throwable);
        final int lineNumber = throwable.getStackTrace()[0].getLineNumber();

        assertEquals("java.lang.RuntimeException", error.getClassName());
        assertEquals("RuntimeException at line " + lineNumber, error.getMessage());
        assertNotNull(error.getBacktrace());
    }

    @Test
    public void testToXml() {
        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);
        System.out.println(stream.toXML(error));
    }

}
