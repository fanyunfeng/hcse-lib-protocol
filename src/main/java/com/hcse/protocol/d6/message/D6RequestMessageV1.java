package com.hcse.protocol.d6.message;

public class D6RequestMessageV1 extends D6RequestMessage {

    public D6RequestMessageV1(String searchString) {
        super(searchString);
        super.setVersion(1);
    }
}
