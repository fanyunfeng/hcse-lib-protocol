package com.hcse.protocol.d6.factory;

import com.hcse.protocol.d6.message.D6ResponseMessageClientDoc;

public class D6ResponseMessageFactory4JsonClient extends D6ResponseMessageFactory {
    public D6ResponseMessageClientDoc createResponseMessageDoc() {
        return new D6ResponseMessageClientDoc();
    }
}
