package com.hcse.cache.service;

import java.net.MalformedURLException;

import com.hcse.cache.protocol.factory.CacheResponseMessageFactory;
import com.hcse.cache.protocol.message.CacheRequestMessage;
import com.hcse.cache.protocol.message.CacheResponseMessage;

public interface CacheService {
    public CacheResponseMessage search(CacheRequestMessage reqMessage, CacheResponseMessageFactory factory)
            throws MalformedURLException;
}
