package com.hcse.d6.service;

import java.net.MalformedURLException;

import com.hcse.d6.protocol.factory.D6ResponseMessageFactory;
import com.hcse.d6.protocol.message.D6RequestMessage;
import com.hcse.d6.protocol.message.D6ResponseMessage;
import com.hcse.service.ServiceException;

public interface DataService {
    public D6ResponseMessage search(D6RequestMessage request, D6ResponseMessageFactory factory)
            throws ServiceException, MalformedURLException;
}
