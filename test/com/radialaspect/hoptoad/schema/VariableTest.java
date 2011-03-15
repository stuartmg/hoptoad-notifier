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
