package com.hcse.d6.protocol.factory;

import com.hcse.d6.protocol.message.D6ResponseMessageClientDoc;

public class D6ResponseMessageFactory4JsonClient extends D6ResponseMessageFactory {
    public D6ResponseMessageClientDoc createResponseMessageDoc() {
        return new D6ResponseMessageClientDoc();
    }
}
