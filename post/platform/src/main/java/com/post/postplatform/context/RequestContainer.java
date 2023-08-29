package com.post.postplatform.context;

import java.util.Collections;

public class RequestContainer {

    private static final RequestContext DEFAULT_VALUE = new RequestContext(Collections.EMPTY_MAP);

    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT = ThreadLocal.withInitial(() -> DEFAULT_VALUE);

    public static void setRequestContext(RequestContext requestContext) {
        if (REQUEST_CONTEXT.get().equals(DEFAULT_VALUE)) {
            REQUEST_CONTEXT.set(requestContext);
        }
        else
        {
            throw new RuntimeException("Cannot override request context");
        }
    }

    public static RequestContext getRequestContext() {
        return REQUEST_CONTEXT.get();
    }

    public static void unsetRequestContext() {
        if (!REQUEST_CONTEXT.get().equals(DEFAULT_VALUE)) {
            REQUEST_CONTEXT.remove();
        }
        else
        {
            throw new RuntimeException("Attempting to unset non existent request context");
        }
    }
}
