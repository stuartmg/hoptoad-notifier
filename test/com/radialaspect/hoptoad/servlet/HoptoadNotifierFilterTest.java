package com.radialaspect.hoptoad.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.junit.Before;
import org.junit.Test;

public class HoptoadNotifierFilterTest {
    private HoptoadNotifierFilter filter;

    @Before
    public void setUp() {
        filter = new HoptoadNotifierFilter();
    }

    @Test
    public void testDoFilter() throws Exception {
        final HttpServletRequest req = mock(HttpServletRequest.class);
        final HttpServletResponse res = mock(HttpServletResponse.class);
        final TestFilterChain chain = new TestFilterChain();

        filter.doFilter(req, res, chain);

        assertEquals(req, chain.request);
        assertNull(MDC.get("request"));
    }

    private class TestFilterChain implements FilterChain {
        public HttpServletRequest request;

        @Override
        public void doFilter(final ServletRequest req, final ServletResponse res) {
            request = (HttpServletRequest) MDC.get("request");
        }
    }
}
