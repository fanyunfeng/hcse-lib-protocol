package com.hcse.service.d6;

import org.springframework.stereotype.Service;

import com.hcse.protocol.d6.codec.D6ClientCodecFactory;
import com.hcse.protocol.d6.message.D6RequestMessage;
import com.hcse.protocol.d6.message.D6ResponseMessage;
import com.hcse.service.BaseService;

@Service
public class DataServiceImpl extends BaseService<D6ResponseMessage, D6RequestMessage, D6ClientCodecFactory> implements
        DataService {
}
