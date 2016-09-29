package com.hcse.protocol.handler;

import com.hcse.protocol.BaseResponse;

public interface ResponseHandler {
    void reset();

    void process(BaseResponse res);
}
