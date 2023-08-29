package com.post.postplatform.context;

import java.util.Collections;
import java.util.Map;

public class RequestContext {
    private final Map<String, String> requestHeaders;

    public RequestContext(Map<String, String> requestHeaders) {
        this.requestHeaders = Collections.unmodifiableMap(requestHeaders);
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
