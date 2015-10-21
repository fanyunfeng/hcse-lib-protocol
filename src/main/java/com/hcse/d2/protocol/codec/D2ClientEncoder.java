package com.hcse.d2.protocol.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolEncoder;

import com.hcse.d2.protocol.message.D2RequestMessage;

public class D2ClientEncoder extends DemuxingProtocolEncoder {
	public D2ClientEncoder() {
		this.addMessageEncoder(D2RequestMessage.class, D2RequestMessageEncoder.class);
	}
}