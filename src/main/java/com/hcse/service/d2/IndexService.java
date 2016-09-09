package com.hcse.service.d2;

import com.hcse.protocol.d2.codec.D2ClientCodecFactory;
import com.hcse.protocol.d2.message.D2RequestMessage;
import com.hcse.protocol.d2.message.D2ResponseMessage;
import com.hcse.service.SearchService;

public interface IndexService extends SearchService<D2RequestMessage, D2ResponseMessage, D2ClientCodecFactory> {

}
