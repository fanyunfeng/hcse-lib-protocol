package com.hcse.protocol.d2.factory;

import com.hcse.protocol.d2.message.D2ResponseMessage;
import com.hcse.protocol.d2.message.D2ResponseMessageDoc;

public class D2ResponseMessageFactory {
    public D2ResponseMessage createResponseMessage() {
        return new D2ResponseMessage();
    }

    public D2ResponseMessageDoc createResponseMessageDoc() {
        return new D2ResponseMessageDoc();
    }
}
