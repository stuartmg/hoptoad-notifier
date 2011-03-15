package com.radialaspect.hoptoad.notifier;

import com.radialaspect.hoptoad.schema.Notice;

public interface HoptoadNotifier {
    String DEFAULT_HTTP_URL = "http://hoptoadapp.com/notifier_api/v2/notices";
    String DEFAULT_HTTPS_URL = "https://hoptoadapp.com/notifier_api/v2/notices";

    void setSecure(final boolean secure);

    String notify(final Notice notice);
}
