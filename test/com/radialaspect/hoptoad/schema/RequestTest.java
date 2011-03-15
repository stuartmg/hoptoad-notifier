package com.radialaspect.hoptoad.schema;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class RequestTest {
    private Request request;

    @Before
    public void setUp() {
        request = new Request("http://www.radialaspect.com", null, null);
    }

    @Test
    public void testToXml() {
        for (int i = 0; i < 10; ++i) {
            request.addParam("mykey" + i, "myval" + i);
        }

        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);
        System.out.println(stream.toXML(request));
    }
}
