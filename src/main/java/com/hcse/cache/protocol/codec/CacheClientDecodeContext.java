package com.hcse.cache.protocol.codec;

import java.nio.charset.CharsetDecoder;

import com.hcse.cache.protocol.message.CacheResponseMessage;

public class CacheClientDecodeContext {
    private int state;
    private CacheResponseMessage responseMessage;

    private CharsetDecoder decoder;

    public CacheClientDecodeContext(CharsetDecoder decoder, CacheResponseMessage responseMessage) {
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

    public CacheResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(CacheResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }
}
