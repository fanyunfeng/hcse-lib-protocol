package com.hcse.protocol.cache.factory;

import com.hcse.protocol.cache.message.CacheResponseMessageDoc4Logistic;
import com.hcse.protocol.util.packet.BaseDoc;

public class CacheResponseMessageFactory4Logistics extends CacheResponseMessageFactory {
    public BaseDoc createResponseMessageDoc() {
        return new CacheResponseMessageDoc4Logistic();
    }
}
