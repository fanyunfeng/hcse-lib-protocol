package com.hcse.d6.protocol.codec;

import java.nio.charset.CharsetDecoder;

import com.hcse.d6.protocol.message.D6ResponseMessage;

public class D6ClientDecodeContext {
    private int state;
    private D6ResponseMessage responseMessage;

    private CharsetDecoder decoder;

    public D6ClientDecodeContext(CharsetDecoder decoder, D6ResponseMessage responseMessage) {
        this.decoder = decoder;
        this.responseMessage = responseMessage;
    }

    public int getParseState() {
        return state;
    }

    public void setParseState(int state) {
        this.state = state;
    }

    public CharsetDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(CharsetDecoder decoder) {
        this.decoder = decoder;
    }

    public D6ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(D6ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }
}
