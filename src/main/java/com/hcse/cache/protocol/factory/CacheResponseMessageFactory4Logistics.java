package com.hcse.cache.protocol.factory;

import com.hcse.cache.protocol.message.CacheResponseMessageDoc;
import com.hcse.cache.protocol.message.CacheResponseMessageDoc4Logistic;

public class CacheResponseMessageFactory4Logistics extends CacheResponseMessageFactory {
    public CacheResponseMessageDoc createResponseMessageDoc() {
        return new CacheResponseMessageDoc4Logistic();
    }
}
