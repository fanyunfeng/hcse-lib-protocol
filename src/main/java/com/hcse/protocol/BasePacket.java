package com.hcse.protocol;

import java.nio.charset.CharsetDecoder;

import com.hcse.protocol.util.packet.BaseDoc;

public class BasePacket implements BaseResponseDoc {
    public void dataProcess(CharsetDecoder decoder) throws Exception {

    }

    public <T extends BaseDoc> T getDocument() {
        return null;
    }

    public void dump(int i) {

    }

    @Override
    public long getWeight() {
        return 0;
    }

    @Override
    public void setWeight(long weight) {

    }
}
