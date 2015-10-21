package com.hcse.d2.protocol.factory;

import com.hcse.d2.protocol.message.D2ResponseMessage;
import com.hcse.d2.protocol.message.D2ResponseMessageDoc;

public class D2ResponseMessageFactory {
    public D2ResponseMessage createResponseMessage() {
        return new D2ResponseMessage();
    }

    public D2ResponseMessageDoc createResponseMessageDoc() {
        return new D2ResponseMessageDoc();
    }
}
