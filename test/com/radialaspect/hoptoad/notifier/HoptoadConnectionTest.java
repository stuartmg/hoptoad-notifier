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
