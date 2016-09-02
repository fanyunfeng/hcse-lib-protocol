package com.hcse.service.d2;

import org.springframework.stereotype.Service;

import com.hcse.protocol.d2.codec.D2ClientCodecFactory;
import com.hcse.protocol.d2.message.D2RequestMessage;
import com.hcse.protocol.d2.message.D2ResponseMessage;
import com.hcse.service.BaseService;

@Service
public class IndexServiceImpl extends BaseService<D2ResponseMessage, D2RequestMessage, D2ClientCodecFactory> implements
        IndexService {
}
