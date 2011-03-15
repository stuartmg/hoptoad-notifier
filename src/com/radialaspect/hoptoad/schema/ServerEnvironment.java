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
