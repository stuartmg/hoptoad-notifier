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
