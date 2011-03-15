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
package com.radialaspect.hoptoad.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("request")
public class Request {
    private String url;
    private String component = "";
    private String action;

    private List<Variable> params;
    private List<Variable> session;

    @XStreamAlias("cgi-data")
    private List<Variable> cgiData;

    public Request(final String url, final String component, final String action) {
        setUrl(url);
        setComponent(component);
        setAction(action);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(final String component) {
        if ((component == null) || "".equals(component.trim())) {
            this.component = "";
        } else {
            this.component = component.trim();
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public List<Variable> getParams() {
        return getParams(false);
    }

    public void addParam(final String key, final String value) {
        getParams(true).add(new Variable(key, value));
    }

    public void addParams(final Map<String, String> params) {
        for (final Map.Entry<String, String> entry : params.entrySet()) {
            getParams(true).add(new Variable(entry.getKey(), entry.getValue()));
        }
    }

    public List<Variable> getSession() {
        return getSession(false);
    }

    public void addSessionParam(final String key, final String value) {
        getSession(true).add(new Variable(key, value));
    }

    public void addSessionParams(final Map<String, String> params) {
        for (final Map.Entry<String, String> entry : params.entrySet()) {
            getSession(true).add(new Variable(entry.getKey(), entry.getValue()));
        }
    }

    public List<Variable> getCgiData() {
        return getCgiData(false);
    }

    public void addCgiData(final String key, final String value) {
        getCgiData(true).add(new Variable(key, value));
    }

    public void addCgiData(final Map<String, String> params) {
        for (final Map.Entry<String, String> entry : params.entrySet()) {
            getCgiData(true).add(new Variable(entry.getKey(), entry.getValue()));
        }
    }

    private List<Variable> getParams(final boolean create) {
        if ((params == null) && create) {
            params = new ArrayList<Variable>();
        }

        return params;
    }

    private List<Variable> getSession(final boolean create) {
        if ((session == null) && create) {
            session = new ArrayList<Variable>();
        }

        return session;
    }

    private List<Variable> getCgiData(final boolean create) {
        if ((cgiData == null) && create) {
            cgiData = new ArrayList<Variable>();
        }

        return cgiData;
    }
}
