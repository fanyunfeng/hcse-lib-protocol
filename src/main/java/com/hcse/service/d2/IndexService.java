package com.hcse.service.d2;

import java.net.MalformedURLException;

import com.hcse.protocol.d2.codec.D2ClientCodecFactory;
import com.hcse.protocol.d2.message.D2RequestMessage;
import com.hcse.protocol.d2.message.D2ResponseMessage;
import com.hcse.service.ServiceException;

public interface IndexService {
    public D2ResponseMessage search(D2RequestMessage request, D2ClientCodecFactory factory) throws ServiceException,
            MalformedURLException;
}
