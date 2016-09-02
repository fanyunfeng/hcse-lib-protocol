package com.hcse.protocol.d6.message;

public class D6RequestMessageV2 extends D6RequestMessage {

    public D6RequestMessageV2(String searchString) {
        super(searchString);
        super.setVersion(2);
    }
}
