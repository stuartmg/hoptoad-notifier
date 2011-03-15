package com.radialaspect.hoptoad.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.MDC;

public class HoptoadNotifierFilter implements Filter {

    @Override
    public void init(final FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        try {
            MDC.put("request", request);

            chain.doFilter(request, response);
        } finally {
            MDC.remove("request");
        }
    }

    @Override
    public void destroy() {
    }
}
