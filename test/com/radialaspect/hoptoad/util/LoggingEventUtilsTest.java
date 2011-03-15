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
