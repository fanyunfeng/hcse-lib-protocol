package com.hcse.service;

import java.net.MalformedURLException;

import org.apache.mina.filter.codec.ProtocolCodecFactory;

public interface SearchService<RequestMessage, ResponseMessage, ResponseCodecFactory extends ProtocolCodecFactory> {
    public void open(ResponseCodecFactory factory);

    public void close();

    public ResponseMessage search(RequestMessage request, ResponseCodecFactory factory) throws ServiceException,
            MalformedURLException;
}
