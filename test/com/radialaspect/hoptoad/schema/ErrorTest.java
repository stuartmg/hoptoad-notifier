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
