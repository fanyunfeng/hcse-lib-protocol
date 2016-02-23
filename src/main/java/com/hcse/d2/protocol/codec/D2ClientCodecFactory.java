package com.hcse.d2.protocol.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.hcse.d2.protocol.factory.D2ResponseMessageFactory;

public class D2ClientCodecFactory extends DemuxingProtocolCodecFactory {
    private D2ClientEncoder requestEncoder;
    private D2ResponseMessageDecoder responseDecoder;

    public D2ClientCodecFactory(D2ResponseMessageFactory factory) {
        this.requestEncoder = new D2ClientEncoder();
        this.responseDecoder = new D2ResponseMessageDecoder(factory);
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
