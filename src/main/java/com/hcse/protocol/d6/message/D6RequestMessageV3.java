package com.hcse.protocol.d6.message;

public class D6RequestMessageV3 extends D6RequestMessage {

    public D6RequestMessageV3(String searchString) {
        super(searchString);
        super.setVersion(3);
    }
}
