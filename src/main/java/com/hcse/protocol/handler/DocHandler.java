package com.hcse.protocol.handler;

import com.hcse.protocol.BaseResponseDoc;

public interface DocHandler {
    void reset();

    void process(BaseResponseDoc doc);
}
