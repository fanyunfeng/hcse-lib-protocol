package com.hcse.protocol.d6.factory;

import com.hcse.protocol.d6.message.D6ResponseMessage;
import com.hcse.protocol.d6.message.D6ResponseMessageClientDoc;

public class D6ResponseMessageFactory {
    public D6ResponseMessage createResponseMessage() {
        return new D6ResponseMessage();
    }

    public D6ResponseMessageClientDoc createResponseMessageDoc() {
        return new D6ResponseMessageClientDoc();
    }
}
