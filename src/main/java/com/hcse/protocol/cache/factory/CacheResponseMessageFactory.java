package com.hcse.protocol.cache.factory;

import com.hcse.protocol.cache.message.CacheResponseMessage;
import com.hcse.protocol.util.packet.BaseDoc;

public class CacheResponseMessageFactory {
    public CacheResponseMessage createResponseMessage() {
        return new CacheResponseMessage();
    }

    public BaseDoc createResponseMessageDoc() {
        return new BaseDoc();
    }
}
