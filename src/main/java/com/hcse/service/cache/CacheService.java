package com.hcse.service.cache;

import java.net.MalformedURLException;

import com.hcse.protocol.cache.codec.CacheClientCodecFactory;
import com.hcse.protocol.cache.message.CacheRequestMessage;
import com.hcse.protocol.cache.message.CacheResponseMessage;
import com.hcse.service.ServiceException;

public interface CacheService {
    public CacheResponseMessage search(CacheRequestMessage reqMessage, CacheClientCodecFactory factory)
            throws ServiceException, MalformedURLException;
}
