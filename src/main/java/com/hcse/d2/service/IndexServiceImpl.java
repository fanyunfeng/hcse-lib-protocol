package com.hcse.d2.service;

import org.springframework.stereotype.Service;

import com.hcse.d2.protocol.codec.D2ClientCodecFactory;
import com.hcse.d2.protocol.message.D2RequestMessage;
import com.hcse.d2.protocol.message.D2ResponseMessage;
import com.hcse.service.BaseService;

@Service
public class IndexServiceImpl extends BaseService<D2ResponseMessage, D2RequestMessage, D2ClientCodecFactory> implements
        IndexService {
}
