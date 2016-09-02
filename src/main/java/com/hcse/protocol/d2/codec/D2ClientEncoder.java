package com.hcse.protocol.d2.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolEncoder;

import com.hcse.protocol.d2.message.D2RequestMessage;

public class D2ClientEncoder extends DemuxingProtocolEncoder {
	public D2ClientEncoder() {
		this.addMessageEncoder(D2RequestMessage.class, D2RequestMessageEncoder.class);
	}
}