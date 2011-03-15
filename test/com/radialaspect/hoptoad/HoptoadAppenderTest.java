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
package com.radialaspect.hoptoad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.radialaspect.hoptoad.notifier.HoptoadException;
import com.radialaspect.hoptoad.notifier.HoptoadNotifier;
import com.radialaspect.hoptoad.notifier.SSLNotSupportedException;
import com.radialaspect.hoptoad.schema.Notice;

public class HoptoadAppenderTest {
    public static final String API_KEY = "abc123";
    public static final String ENVIRONMENT = "test";

    private HoptoadAppender appender;
    private PackageFilter filter;
    private Logger rootLogger;
    private Logger logger;

    // mock notifier
    private TestNotifier notifier;

    @Before
    public void setUp() {
        notifier = new TestNotifier(HttpURLConnection.HTTP_OK, "<notice><id>1</td><url>http://test.com</url></notice>");

        filter = new PackageFilter();
        filter.setPackageNames("com.radialaspect");

        appender = new HoptoadAppender();
        appender.setApiKey(API_KEY);
        appender.setEnvironment(ENVIRONMENT);
        appender.setNotifier(notifier);
        appender.setSsl(true);
        appender.addFilter(filter);

        rootLogger = Logger.getRootLogger();
        rootLogger.removeAllAppenders();
        rootLogger.addAppender(appender);

        logger = Logger.getLogger("test");
    }

    @Test
    public void testApiKey() throws Exception {
        assertEquals(API_KEY, appender.getApiKey());
    }

    @Test
    public void testEnvironment() throws Exception {
        assertEquals(ENVIRONMENT, appender.getEnvironment());
    }

    @Test
    public void testNotifier() throws Exception {
        assertEquals(notifier, appender.getNotifier());
    }

    @Test
    public void testSsl() throws Exception {
        assertTrue(appender.isSsl());
    }

    @Test
    public void testWithMessageAndError() throws Exception {
        logger.error("An error occurred", new RuntimeException("test exception"));

        assertNotNull(notifier.notice);
    }

    @Test
    public void testWithJustError() throws Exception {
        logger.error(new RuntimeException("test exception"));

        assertNotNull(notifier.notice);
    }

    @Test
    public void testWithJustMessage() throws Exception {
        logger.error("An error occurred");

        assertNull(notifier.notice);
    }

    @Test
    public void testWithNoApiKey() throws Exception {
        appender.setApiKey(null);

        logger.error(new RuntimeException("test exception"));

        assertNull(notifier.notice);
    }

    @Test
    public void testAppendWhenSSLIsNotSupported() throws Exception {
        notifier = new TestNotifier(HttpURLConnection.HTTP_FORBIDDEN, "<notice><id>1</td><url>http://test.com</url></notice>");
        appender.setNotifier(notifier);

        logger.error(new RuntimeException("test exception"));

        assertFalse(appender.isSsl());
        assertFalse(notifier.secure);
        assertNotNull(notifier.notice);
    }

    @Test
    public void testAppendWhenNotifierErrors() throws Exception {
        notifier = new TestNotifier(HttpURLConnection.HTTP_INTERNAL_ERROR, null);
        appender.setNotifier(notifier);

        logger.error(new RuntimeException("test exception"));

        assertNull(notifier.notice);
    }

    private class TestNotifier implements HoptoadNotifier {
        public Notice notice;
        public int responseCode;
        public String response;
        public boolean secure;

        public TestNotifier(final int responseCode, final String response) {
            this.responseCode = responseCode;
            this.response = response;
        }

        @Override
        public String notify(final Notice notice) {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                this.notice = notice;
                return response;
            } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                responseCode = HttpURLConnection.HTTP_OK;
                throw new SSLNotSupportedException(HttpURLConnection.HTTP_FORBIDDEN);
            } else {
                throw new HoptoadException(responseCode);
            }

        }

        @Override
        public void setSecure(final boolean secure) {
            this.secure = secure;
        }
    }
}
