package com.hcse.util.sstring;

import com.hcse.protocol.BaseRequest;

public interface RequestFactory<Request extends BaseRequest> {
    public Request create();
}
