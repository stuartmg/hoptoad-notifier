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
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ServerEnvironmentTest {
    public static final String PROJECT_ROOT = "/myproject";
    public static final String ENVIRONMENT = "test";

    private ServerEnvironment env;

    @Before
    public void setUp() {
        env = new ServerEnvironment(PROJECT_ROOT, ENVIRONMENT);
    }

    @Test
    public void testDefaultEnvironment() {
        env = new ServerEnvironment(PROJECT_ROOT);

        assertEquals(ServerEnvironment.DEFAULT_ENVIRONMENT, env.getEnvironment());
    }

    @Test
    public void testProjectRoot() {
        env.setProjectRoot("test");

        assertEquals("test", env.getProjectRoot());
    }

    @Test
    public void testProjectRootWithNull() {
        env.setProjectRoot(null);

        assertNull(env.getProjectRoot());
    }

    @Test
    public void testProjectRootWithEmptyString() {
        env.setProjectRoot("  ");

        assertNull(env.getProjectRoot());
    }

    @Test
    public void testEnvironment() {
        env.setEnvironment("test");

        assertEquals("test", env.getEnvironment());
    }

    @Test
    public void testEnvironmentWithNull() {
        env.setEnvironment(null);

        assertEquals(ServerEnvironment.DEFAULT_ENVIRONMENT, env.getEnvironment());
    }

    @Test
    public void testEnvironmentWithEmptyString() {
        env.setEnvironment("   ");

        assertEquals(ServerEnvironment.DEFAULT_ENVIRONMENT, env.getEnvironment());
    }

    @Test
    public void testToXml() {
        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        final String output = stream.toXML(env);

        assertTrue(output.contains("<server-environment>"));
        assertTrue(output.contains("</server-environment>"));
        assertTrue(output.contains("<project-root>" + PROJECT_ROOT + "</project-root>"));
        assertTrue(output.contains("<environment-name>" + ENVIRONMENT + "</environment-name>"));
    }
}
