package com.radialaspect.hoptoad.notifier;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;

public class HoptoadConnectionTest {
    private HoptoadConnection connection;

    @Test
    public void testGetUrl() throws Exception {
        final String url = "http://localhost/notifier";

        connection = new HoptoadConnection(url, false);

        assertEquals(new URL(url), connection.getUrl());
    }

    @Test
    public void testGetDefaultUrl() throws Exception {
        connection = new HoptoadConnection(null, false);

        assertEquals(new URL(HoptoadNotifier.DEFAULT_HTTP_URL), connection.getUrl());
    }

    @Test
    public void testGetDefaultSecureUrl() throws Exception {
        connection = new HoptoadConnection(null, true);

        assertEquals(new URL(HoptoadNotifier.DEFAULT_HTTPS_URL), connection.getUrl());
    }
}
