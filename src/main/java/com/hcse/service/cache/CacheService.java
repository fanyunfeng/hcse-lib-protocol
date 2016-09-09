package com.hcse.service.cache;

import com.hcse.protocol.cache.codec.CacheClientCodecFactory;
import com.hcse.protocol.cache.message.CacheRequestMessage;
import com.hcse.protocol.cache.message.CacheResponseMessage;
import com.hcse.service.SearchService;

public interface CacheService extends SearchService<CacheRequestMessage, CacheResponseMessage, CacheClientCodecFactory> {

}
