package com.radialaspect.hoptoad.notifier;

public class SSLNotSupportedException extends HoptoadException {
    private static final long serialVersionUID = -119218665239521051L;

    public SSLNotSupportedException(final int responseCode) {
        super(responseCode);
    }

    public SSLNotSupportedException(final int responseCode, final String response) {
        super(responseCode, response);
    }
}
