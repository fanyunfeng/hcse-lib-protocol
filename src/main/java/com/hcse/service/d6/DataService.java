package com.hcse.service.d6;

import java.net.MalformedURLException;

import com.hcse.protocol.d6.codec.D6ClientCodecFactory;
import com.hcse.protocol.d6.message.D6RequestMessage;
import com.hcse.protocol.d6.message.D6ResponseMessage;
import com.hcse.service.ServiceException;

public interface DataService {
    public D6ResponseMessage search(D6RequestMessage request, D6ClientCodecFactory factory)
            throws ServiceException, MalformedURLException;
}
