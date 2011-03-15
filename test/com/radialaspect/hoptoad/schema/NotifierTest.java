package com.radialaspect.hoptoad.schema;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class NotifierTest {
    private final Notifier notifier = Notifier.getInstance();

    @Test
    public void testToXml() throws Exception {
        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);
        System.out.println(stream.toXML(notifier));
    }
}
