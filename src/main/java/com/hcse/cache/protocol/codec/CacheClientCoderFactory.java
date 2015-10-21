package com.hcse.cache.protocol.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.hcse.cache.protocol.factory.CacheResponseMessageFactory;

public class CacheClientCoderFactory extends DemuxingProtocolCodecFactory {
    private CacheClientEncoder requestEncoder;
    private CacheResponseMessageDecoder responseDecoder;

    public CacheClientCoderFactory(CacheResponseMessageFactory factory) {
        this.requestEncoder = new CacheClientEncoder();
        this.responseDecoder = new CacheResponseMessageDecoder(factory);
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
