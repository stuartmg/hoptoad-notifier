package com.radialaspect.hoptoad.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class NoticeTest {
    private Notice notice;
    private Throwable throwable;
    private ServerEnvironment environment;
    private Request request;

    @Before
    public void setUp() {
        throwable = new RuntimeException("test exception");
        environment = new ServerEnvironment("/myapp", "staging");
        request = new Request("/myapp/users", null, null);

        for (int i = 0; i < 10; ++i) {
            request.addParam("param" + i, "value" + i);
            request.addSessionParam("session" + i, "value" + i);
            request.addCgiData("cgidata" + i, "value" + i);
        }

        notice = new Notice("1234567890", throwable, environment, request);
    }

    @Test
    public void testUrl() throws Exception {
        request.setUrl("test");

        assertEquals("test", request.getUrl());
    }

    @Test
    public void testUrlWithNull() throws Exception {
        request.setUrl(null);

        assertNull(request.getUrl());
    }

    @Test
    public void testComponent() throws Exception {
        request.setComponent("test");

        assertEquals("test", request.getComponent());
    }

    @Test
    public void testComponentWithNull() throws Exception {
        request.setComponent(null);

        assertEquals("", request.getComponent());
    }

    @Test
    public void testComponentWithEmptyString() throws Exception {
        request.setComponent("   ");

        assertEquals("", request.getComponent());
    }

    @Test
    public void testAction() throws Exception {
        request.setAction("test");

        assertEquals("test", request.getAction());
    }

    @Test
    public void testActionWithNull() throws Exception {
        request.setAction(null);

        assertNull(request.getAction());
    }

    @Test
    public void testToXml() {
        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        final String output = stream.toXML(notice);

        System.out.println(output);
        assertTrue(isValid(output));
    }

    private boolean isValid(final String xml) {
        try {
            final SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            final File schemaFile = new File("test/hoptoad.xsd");
            final Schema schema = factory.newSchema(schemaFile);
            final Validator validator = schema.newValidator();
            final Source source = new StreamSource(new StringReader(xml));

            validator.validate(source);
            return true;
        } catch (final Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
