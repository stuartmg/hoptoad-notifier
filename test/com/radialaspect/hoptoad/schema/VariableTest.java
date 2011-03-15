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

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class VariableTest {
    public static final String KEY = "mykey";
    public static final String VALUE = "myval";

    private Variable variable;

    @Before
    public void setUp() {
        variable = new Variable(KEY, VALUE);
    }

    @Test
    public void testKey() throws Exception {
        variable.setKey("test");

        assertEquals("test", variable.getKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKeyWithNull() throws Exception {
        variable.setKey(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKeyWithEmptyString() throws Exception {
        variable.setKey("   ");
    }

    @Test
    public void testValue() throws Exception {
        variable.setValue("test");

        assertEquals("test", variable.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueWithNull() throws Exception {
        variable.setValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueWithEmptyString() throws Exception {
        variable.setValue("   ");
    }

    @Test
    public void testToXml() {
        final String expected = "<var key=\"" + KEY + "\">" + VALUE + "</var>";

        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        assertEquals(expected, stream.toXML(variable));
    }
}
