package com.hcse.d6.protocol.factory;

import com.hcse.d6.protocol.message.D6ResponseMessageClientDoc;
import com.hcse.protocol.util.packet.FieldsMap;

public class D6ResponseMessageFactory4Logistic extends D6ResponseMessageFactory {
    public D6ResponseMessageClientDoc createResponseMessageDoc() {
        return new D6ResponseMessageClientDoc(FieldsMap.create(1));
    }
}
