package com.hcse.cache.protocol.factory;

import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.cache.protocol.message.CacheResponseMessageDoc;

public class CacheResponseMessageFactory {
    public CacheResponseMessage createResponseMessage() {
        return new CacheResponseMessage();
    }

    public CacheResponseMessageDoc createResponseMessageDoc() {
        return new CacheResponseMessageDoc();
    }
}
