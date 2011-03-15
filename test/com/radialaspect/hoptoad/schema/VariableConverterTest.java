package com.radialaspect.hoptoad.schema;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class VariableConverterTest {
    private VariableConverter converter;

    @Before
    public void setUp() {
        converter = new VariableConverter();
    }

    @Test
    public void testCanConvert() {
        assertTrue(converter.canConvert(Variable.class));
        assertFalse(converter.canConvert(String.class));
    }

    @Test
    public void testCanConvertWithNull() {
        assertFalse(converter.canConvert(null));
    }

    @Test
    public void testUnmarshall() {
        assertNull(converter.unmarshal(null, null));
    }
}
