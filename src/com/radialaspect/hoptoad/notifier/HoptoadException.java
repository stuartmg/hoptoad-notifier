package com.radialaspect.hoptoad.notifier;

public class HoptoadException extends RuntimeException {
    private static final long serialVersionUID = 6254246886098831847L;

    private int responseCode;
    private String response;

    public HoptoadException(final int responseCode) {
        super("An error occurred while sending a notice to Hoptoad: " + responseCode);
        setResponseCode(responseCode);
    }

    public HoptoadException(final int responseCode, final String response) {
        super("An error occurred while sending a notice to Hoptoad: " + responseCode);
        setResponseCode(responseCode);
        setResponse(response);
    }

    public HoptoadException(final Throwable cause) {
        super(cause);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(final int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(final String response) {
        this.response = response;
    }
}
