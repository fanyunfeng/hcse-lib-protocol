package com.hcse.d2.protocol.codec;

import java.nio.charset.CharsetDecoder;

import com.hcse.d2.protocol.message.D2ResponseMessage;

public class D2ClientDecodeContext {
    private int state;
    private D2ResponseMessage responseMessage;

    private CharsetDecoder decoder;

    public D2ClientDecodeContext(CharsetDecoder decoder, D2ResponseMessage response) {
        this.decoder = decoder;
        this.responseMessage = response;
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

    public D2ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(D2ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }
}
