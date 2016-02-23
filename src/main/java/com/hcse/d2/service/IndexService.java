package com.hcse.d2.service;

import java.net.MalformedURLException;

import com.hcse.d2.protocol.codec.D2ClientCodecFactory;
import com.hcse.d2.protocol.message.D2RequestMessage;
import com.hcse.d2.protocol.message.D2ResponseMessage;
import com.hcse.service.ServiceException;

public interface IndexService {
    public D2ResponseMessage search(D2RequestMessage request, D2ClientCodecFactory factory) throws ServiceException,
            MalformedURLException;
}
