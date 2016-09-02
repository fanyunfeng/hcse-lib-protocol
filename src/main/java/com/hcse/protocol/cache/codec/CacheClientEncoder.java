package com.hcse.protocol.cache.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolEncoder;

import com.hcse.protocol.cache.message.CacheRequestMessage;


public class CacheClientEncoder extends DemuxingProtocolEncoder{
	public CacheClientEncoder(){
		this.addMessageEncoder(CacheRequestMessage.class, CacheRequestMessageEncoder.class);
	}
}
