package com.hcse.service.cache;

import org.springframework.stereotype.Service;

import com.hcse.protocol.cache.codec.CacheClientCodecFactory;
import com.hcse.protocol.cache.message.CacheRequestMessage;
import com.hcse.protocol.cache.message.CacheResponseMessage;
import com.hcse.service.BaseService;

@Service
public class CacheServiceImpl extends BaseService<CacheRequestMessage, CacheResponseMessage, CacheClientCodecFactory>
        implements CacheService {
}