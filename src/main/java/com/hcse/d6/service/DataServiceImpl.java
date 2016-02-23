package com.hcse.d6.service;

import org.springframework.stereotype.Service;

import com.hcse.d6.protocol.codec.D6ClientCodecFactory;
import com.hcse.d6.protocol.message.D6RequestMessage;
import com.hcse.d6.protocol.message.D6ResponseMessage;

import com.hcse.service.BaseService;

@Service
public class DataServiceImpl extends BaseService<D6ResponseMessage, D6RequestMessage, D6ClientCodecFactory> implements
        DataService {
}
