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
package com.radialaspect.hoptoad.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.junit.Before;
import org.junit.Test;

public class LoggingEventUtilsTest {

    // mocks
    private Throwable throwable;
    private LoggingEvent event;
    private ThrowableInformation info;

    @Before
    public void setUp() {
        throwable = new RuntimeException("test exception");
        event = mock(LoggingEvent.class);
        info = mock(ThrowableInformation.class);
    }

    @Test
    public void testLoggingEventWithMessageAndError() {
        when(event.getThrowableInformation()).thenReturn(info);
        when(info.getThrowable()).thenReturn(throwable);

        final Throwable t = LoggingEventUtils.getThrowableFromEvent(event);

        assertEquals(t, throwable);
    }

    @Test
    public void testLoggingEventWithJustError() {
        when(event.getThrowableInformation()).thenReturn(null);
        when(event.getMessage()).thenReturn(throwable);

        final Throwable t = LoggingEventUtils.getThrowableFromEvent(event);

        assertEquals(t, throwable);
    }

    @Test
    public void testLoggingEventWithJustMessage() {
        when(event.getThrowableInformation()).thenReturn(null);
        when(event.getMessage()).thenReturn("a message");

        assertNull(LoggingEventUtils.getThrowableFromEvent(event));
    }
}
