package com.hcse.cache.service;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcse.cache.protocol.codec.CacheClientCoderFactory;
import com.hcse.cache.protocol.factory.CacheResponseMessageFactory;
import com.hcse.cache.protocol.message.CacheRequestMessage;
import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.service.common.ServiceDiscoveryService;

@Service
public class CacheServiceImpl implements CacheService {
    protected final Logger logger = Logger.getLogger(CacheServiceImpl.class);

    @Autowired
    ServiceDiscoveryService serviceDiscovery;

    private int maxRetryTimes;

    public CacheServiceImpl() {
        maxRetryTimes = 2;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public CacheResponseMessage search(CacheRequestMessage reqMessage, CacheResponseMessageFactory factory)
            throws MalformedURLException {

        logger.info("request to cache:" + reqMessage);

        int socketTimeout = 600;

        CacheResponseMessage resp = null;

        int retryTimes = 1;
        while (retryTimes < maxRetryTimes) {
            NioSocketConnector connector = new NioSocketConnector();

            connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CacheClientCoderFactory(factory)));
            connector.setConnectTimeoutMillis(socketTimeout);
            connector.getSessionConfig().setUseReadOperation(true);

            ConnectFuture cf = connector.connect(serviceDiscovery.lookup(reqMessage.getServiceAddress()));

            cf.awaitUninterruptibly();
            try {
                if (cf.isConnected()) {
                    cf.getSession().write(reqMessage);

                    ReadFuture readFuture = cf.getSession().read();

                    if (readFuture.awaitUninterruptibly(socketTimeout, TimeUnit.SECONDS)) {
                        resp = (CacheResponseMessage) readFuture.getMessage();
                        if (resp != null) {
                            break;
                        } else {
                            retryTimes++;
                        }
                    }
                    cf.getSession().getCloseFuture().awaitUninterruptibly();
                } else {
                    retryTimes++;
                }
            } catch (Exception e) {
                logger.error("socket exception:", e);
                retryTimes++;
            } finally {
                if (!cf.isCanceled()) {
                    cf.getSession().close(true);
                    logger.info("session exception.");
                }
                if (!connector.isDisposed()) {
                    connector.dispose();
                    logger.info("connection exception.");
                }
            }
        }
        return resp;
    }
}
