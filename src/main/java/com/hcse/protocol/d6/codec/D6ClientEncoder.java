package com.hcse.protocol.d6.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolEncoder;

import com.hcse.protocol.d6.message.D6RequestMessage;

public class D6ClientEncoder extends DemuxingProtocolEncoder {
    public D6ClientEncoder() {
        this.addMessageEncoder(D6RequestMessage.class, D6RequestMessageEncoder.class);
    }
}