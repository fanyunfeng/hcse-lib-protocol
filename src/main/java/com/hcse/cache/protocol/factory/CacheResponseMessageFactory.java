package com.hcse.cache.protocol.factory;

import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.protocol.util.packet.BaseDoc;

public class CacheResponseMessageFactory {
    public CacheResponseMessage createResponseMessage() {
        return new CacheResponseMessage();
    }

    public BaseDoc createResponseMessageDoc() {
        return new BaseDoc();
    }
}
