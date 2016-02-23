package com.hcse.cache.service;

import java.net.MalformedURLException;

import com.hcse.cache.protocol.codec.CacheClientCodecFactory;
import com.hcse.cache.protocol.message.CacheRequestMessage;
import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.service.ServiceException;

public interface CacheService {
    public CacheResponseMessage search(CacheRequestMessage reqMessage, CacheClientCodecFactory factory)
            throws ServiceException, MalformedURLException;
}
