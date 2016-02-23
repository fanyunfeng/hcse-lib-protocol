package com.hcse.cache.service;

import org.springframework.stereotype.Service;

import com.hcse.cache.protocol.codec.CacheClientCodecFactory;
import com.hcse.cache.protocol.message.CacheRequestMessage;
import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.service.BaseService;

@Service
public class CacheServiceImpl extends BaseService<CacheResponseMessage, CacheRequestMessage, CacheClientCodecFactory>
        implements CacheService {
}