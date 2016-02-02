package com.hcse.protocol.util.packet;

import java.nio.charset.CharsetDecoder;

public class BasePacket {
    public void dataProcess(CharsetDecoder decoder) throws Exception {

    }

    public <T extends BaseDoc> T getDocument() {
        return null;
    }

    public void dump(int i) {

    }
}
