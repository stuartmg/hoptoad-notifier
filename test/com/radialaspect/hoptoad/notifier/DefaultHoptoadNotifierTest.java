package com.radialaspect.hoptoad.notifier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;

import com.radialaspect.hoptoad.HoptoadNoticeBuilder;
import com.radialaspect.hoptoad.schema.Notice;
import com.thoughtworks.xstream.XStream;

public class DefaultHoptoadNotifierTest {
    private DefaultHoptoadNotifier notifier;

    // mocks
    private HoptoadConnection hoptoadConnection;
    private HttpURLConnection connection;
    private ByteArrayOutputStream out;

    private Notice notice;

    @Before
    public void setUp() throws Exception {
        hoptoadConnection = mock(HoptoadConnection.class);
        connection = mock(HttpURLConnection.class);
        out = new ByteArrayOutputStream();

        notice = new HoptoadNoticeBuilder().apiKey("abc123").environment("test").error(new RuntimeException("test exception")).build();

        notifier = new DefaultHoptoadNotifier("http://localhost/notifier", true);
        notifier.setConnection(hoptoadConnection);
    }

    @Test
    public void testNotify() throws Exception {
        final String expected = "<notice><id>222</id><url>http://example.hoptoadapp.com/errors/132343546/notices/222</url></notice>";

        when(hoptoadConnection.connect()).thenReturn(connection);
        when(connection.getOutputStream()).thenReturn(out);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(expected.getBytes()));
        when(connection.getResponseCode()).thenReturn(200);

        final String actual = notifier.notify(notice);

        verify(connection).setDoOutput(true);
        verify(connection).setRequestProperty("Content-type", "text/xml");
        verify(connection).setRequestProperty("Accept", "text/xml, application/xml");
        verify(connection).setRequestMethod("POST");
        verify(connection).disconnect();

        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        final String expectedNotice = stream.toXML(notice);

        assertEquals(expectedNotice, out.toString());
        assertEquals(expected, actual);
    }

    @Test(expected = SSLNotSupportedException.class)
    public void testNotifyWithUnsupportedHTTPS() throws Exception {
        when(hoptoadConnection.connect()).thenReturn(connection);
        when(connection.getOutputStream()).thenReturn(out);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
        when(connection.getResponseCode()).thenReturn(403);

        notifier.notify(notice);

        verify(connection).setDoOutput(true);
        verify(connection).setRequestProperty("Content-type", "application/xml");
        verify(connection).setRequestProperty("Accept", "text/xml, application/xml");
        verify(connection).setRequestMethod("POST");
        verify(connection).disconnect();
    }

    @Test(expected = HoptoadException.class)
    public void testNotifyWithGeneralError() throws Exception {
        when(hoptoadConnection.connect()).thenReturn(connection);
        when(connection.getOutputStream()).thenReturn(out);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
        when(connection.getResponseCode()).thenReturn(500);

        notifier.notify(notice);

        verify(connection).setDoOutput(true);
        verify(connection).setRequestProperty("Content-type", "application/xml");
        verify(connection).setRequestProperty("Accept", "text/xml, application/xml");
        verify(connection).setRequestMethod("POST");
        verify(connection).disconnect();
    }
}
