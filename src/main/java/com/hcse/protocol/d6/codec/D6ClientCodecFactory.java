package com.hcse.protocol.d6.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.hcse.protocol.d6.factory.D6ResponseMessageFactory;

public class D6ClientCodecFactory extends DemuxingProtocolCodecFactory {
    private D6ClientEncoder requestEncoder;
    private D6ResponseMessageDecoder responseDecoder;

    public D6ClientCodecFactory(D6ResponseMessageFactory factory) {
        this.requestEncoder = new D6ClientEncoder();
        this.responseDecoder = new D6ResponseMessageDecoder(factory);
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return responseDecoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return requestEncoder;
    }
}
