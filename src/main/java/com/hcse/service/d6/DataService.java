package com.hcse.service.d6;

import com.hcse.protocol.d6.codec.D6ClientCodecFactory;
import com.hcse.protocol.d6.message.D6RequestMessage;
import com.hcse.protocol.d6.message.D6ResponseMessage;
import com.hcse.service.SearchService;

public interface DataService extends SearchService<D6RequestMessage, D6ResponseMessage, D6ClientCodecFactory> {

}
