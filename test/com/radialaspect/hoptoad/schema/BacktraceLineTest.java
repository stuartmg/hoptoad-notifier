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
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class BacktraceLineTest {
    public static final String FILE_NAME = "filename.java";
    public static final int LINE_NUMBER = 100;
    public static final String METHOD_NAME = "methodName";

    private BacktraceLine line;

    @Before
    public void setUp() {
        line = new BacktraceLine(FILE_NAME, LINE_NUMBER, METHOD_NAME);
    }

    @Test
    public void testShortConstructor() throws Exception {
        line = new BacktraceLine(FILE_NAME, LINE_NUMBER);

        assertEquals(FILE_NAME, line.getFile());
        assertEquals(LINE_NUMBER, line.getLineNumber());
        assertNull(line.getMethod());
    }

    @Test
    public void testFile() throws Exception {
        line.setFile("test");

        assertEquals("test", line.getFile());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileWithNull() throws Exception {
        line.setFile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileWithEmptyString() throws Exception {
        line.setFile("   ");
    }

    @Test
    public void testLineNumber() throws Exception {
        line.setLineNumber(1);

        assertEquals(1, line.getLineNumber());
    }

    @Test
    public void testLineNumberWithZero() throws Exception {
        line.setLineNumber(0);

        assertEquals(0, line.getLineNumber());
    }

    @Test
    public void testLineNumberWithNegative() throws Exception {
        line.setLineNumber(-1);

        assertEquals(-1, line.getLineNumber());
    }

    @Test
    public void testMethod() throws Exception {
        line.setMethod("toString");

        assertEquals("toString", line.getMethod());
    }

    @Test
    public void testMethodWithNull() throws Exception {
        line.setMethod(null);

        assertNull(line.getMethod());
    }

    @Test
    public void testMethodWithEmptyString() throws Exception {
        line.setMethod("  ");

        assertNull(line.getMethod());
    }

    @Test
    public void testToXml() {
        final String expected = "<line file=\"" + FILE_NAME + "\" number=\"" + LINE_NUMBER + "\" method=\"" + METHOD_NAME + "\"/>";

        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        assertEquals(expected, stream.toXML(line));
    }
}
