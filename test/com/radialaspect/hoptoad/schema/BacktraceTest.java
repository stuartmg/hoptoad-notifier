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
