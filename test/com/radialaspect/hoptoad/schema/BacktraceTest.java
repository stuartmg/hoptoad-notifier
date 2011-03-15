package com.radialaspect.hoptoad.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class BacktraceTest {
    private Backtrace backtrace;
    private Throwable throwable;

    @Before
    public void setUp() {
        throwable = new RuntimeException("test exception");
        backtrace = new Backtrace(throwable);
    }

    @Test
    public void testLines() throws Exception {
        assertEquals(throwable.getStackTrace().length, backtrace.getLines().size());

        final StackTraceElement[] elems = throwable.getStackTrace();

        for (int i = 0; i < elems.length; ++i) {
            assertEquals(elems[i].getLineNumber(), backtrace.getLines().get(i).getLineNumber());
            assertEquals(elems[i].getMethodName(), backtrace.getLines().get(i).getMethod());
        }
    }

    @Test
    public void testLinesWithCause() throws Exception {
        final Throwable t = new RuntimeException("with cause", throwable);
        backtrace = new Backtrace(t);

        final List<StackTraceElement> elems = new ArrayList<StackTraceElement>();
        for (final StackTraceElement elem : t.getStackTrace()) {
            elems.add(elem);
        }
        for (final StackTraceElement elem : throwable.getStackTrace()) {
            elems.add(elem);
        }

        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        t.printStackTrace();
        System.out.println(stream.toXML(backtrace));

        for (int i = 0; i < elems.size(); ++i) {
            assertEquals(elems.get(i).getLineNumber(), backtrace.getLines().get(i).getLineNumber());
            assertEquals(elems.get(i).getMethodName(), backtrace.getLines().get(i).getMethod());
        }
    }

    @Test
    public void testToXml() throws Exception {
        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        final String output = stream.toXML(backtrace);
        assertTrue(output.contains("<backtrace>"));
        assertTrue(output.contains("</backtrace>"));

        // TODO: add asserts for lines
    }
}
