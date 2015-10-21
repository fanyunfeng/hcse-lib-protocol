package com.hcse.cache.protocol.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolEncoder;

import com.hcse.cache.protocol.message.CacheRequestMessage;


public class CacheClientEncoder extends DemuxingProtocolEncoder{
	public CacheClientEncoder(){
		this.addMessageEncoder(CacheRequestMessage.class, CacheRequestMessageEncoder.class);
	}
}
