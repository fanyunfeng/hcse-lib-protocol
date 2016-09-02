package com.hcse.protocol.d6.codec;

import java.nio.charset.CharsetDecoder;

import com.hcse.protocol.d6.message.D6ResponseMessage;

public class D6ClientDecodeContext {
    private int state;
    private int version;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
