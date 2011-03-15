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

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("server-environment")
public class ServerEnvironment {
    public static final String DEFAULT_ENVIRONMENT = "production";

    @XStreamAlias("project-root")
    private String projectRoot;

    @XStreamAlias("environment-name")
    private String environment;

    public ServerEnvironment(final String projectRoot) {
        this(projectRoot, DEFAULT_ENVIRONMENT);
    }

    public ServerEnvironment(final String projectRoot, final String environment) {
        setProjectRoot(projectRoot);
        setEnvironment(environment);
    }

    public String getProjectRoot() {
        return projectRoot;
    }

    public void setProjectRoot(final String projectRoot) {
        if ((projectRoot == null) || "".equals(projectRoot.trim())) {
            this.projectRoot = null;
        } else {
            this.projectRoot = projectRoot.trim();
        }
    }

    public String getEnvironment() {
        return (environment == null) ? DEFAULT_ENVIRONMENT : environment;
    }

    public void setEnvironment(final String environment) {
        if ((environment == null) || "".equals(environment.trim())) {
            this.environment = null;
        } else {
            this.environment = environment;
        }
    }
}
