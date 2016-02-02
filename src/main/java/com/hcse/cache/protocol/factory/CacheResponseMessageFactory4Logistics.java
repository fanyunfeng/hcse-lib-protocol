package com.hcse.cache.protocol.factory;

import com.hcse.cache.protocol.message.CacheResponseMessageDoc4Logistic;
import com.hcse.protocol.util.packet.BaseDoc;

public class CacheResponseMessageFactory4Logistics extends CacheResponseMessageFactory {
    public BaseDoc createResponseMessageDoc() {
        return new CacheResponseMessageDoc4Logistic();
    }
}
