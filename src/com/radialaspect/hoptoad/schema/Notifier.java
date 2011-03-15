package com.radialaspect.hoptoad.schema;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("notifier")
public class Notifier {
    public final String name = "hoptoad-notifier";
    public final String version = "0.1";
    public final String url = "";

    // instance
    private static Notifier instance;

    private Notifier() {
        super();
    }

    public static final Notifier getInstance() {
        if (instance == null) {
            instance = new Notifier();
        }

        return instance;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }
}
