package com.hcse.cache.service;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcse.cache.protocol.codec.CacheClientCoderFactory;
import com.hcse.cache.protocol.factory.CacheResponseMessageFactory;
import com.hcse.cache.protocol.message.CacheRequestMessage;
import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.protocol.util.LoggingFilter;
import com.hcse.service.ConnectTimeout;
import com.hcse.service.RequestTimeout;
import com.hcse.service.ServiceException;
import com.hcse.service.common.ServiceDiscoveryService;

@Service
public class CacheServiceImpl implements CacheService {
    protected final Logger logger = Logger.getLogger(CacheServiceImpl.class);

    private ServiceDiscoveryService serviceDiscovery;

    @Autowired
    public void setServiceDiscoveryService(ServiceDiscoveryService service) {
        serviceDiscovery = service;
    }

    private NioSocketConnector connector;

    private int requestTimeout = 600;
    private int connectTimeout = 600;
    private int maxRetryTimes = 2;

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    private NioSocketConnector openConnector(CacheResponseMessageFactory factory) {
        NioSocketConnector connector = new NioSocketConnector();

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CacheClientCoderFactory(factory)));
        connector.setConnectTimeoutMillis(connectTimeout);
        connector.getSessionConfig().setUseReadOperation(true);

        return connector;
    }

    private void closeConnector(NioSocketConnector connector) {
        if (connector != null) {
            if (!connector.isDisposed()) {
                connector.dispose();
                if (logger.isDebugEnabled()) {
                    logger.debug("connector closed.");
                }
            }
        }
    }

    public void open(CacheResponseMessageFactory factory) {
        this.connector = openConnector(factory);
    }

    public void close() {
        closeConnector(connector);
    }

    @Override
    public CacheResponseMessage search(CacheRequestMessage request, CacheResponseMessageFactory factory)
            throws ServiceException, MalformedURLException {

        NioSocketConnector connector = this.connector;

        CacheResponseMessage resp = null;
        IoSession session = null;

        if (factory != null) {
            connector = openConnector(factory);
        }

        int retryTimes = 0;
        while (retryTimes < maxRetryTimes) {
            ConnectFuture cf = connector.connect(serviceDiscovery.lookup(request.getServiceAddress()));

            cf.awaitUninterruptibly();
            try {
                if (cf.isConnected()) {
                    session = cf.getSession();
                    session.write(request);

                    session.suspendWrite();

                    ReadFuture readFuture = session.read();

                    if (readFuture.awaitUninterruptibly(requestTimeout, TimeUnit.MILLISECONDS)) {
                        resp = (CacheResponseMessage) readFuture.getMessage();
                        if (resp != null) {
                            break;
                        } else {
                            retryTimes++;
                            if (retryTimes >= maxRetryTimes) {
                                throw new RequestTimeout();
                            } else {
                                logger.error("response is null.");
                            }
                        }
                    }
                    cf.getSession().getCloseFuture().awaitUninterruptibly();
                } else {
                    retryTimes++;
                    if (retryTimes >= maxRetryTimes) {
                        throw new ConnectTimeout();
                    } else {
                        logger.error("connection is not connected.");
                    }
                }
            } finally {
                if (session != null) {
                    session.close(true);
                    if (logger.isDebugEnabled()) {
                        logger.debug("session closed.");
                    }
                }
            }
        }

        if (connector != this.connector) {
            closeConnector(connector);
        }

        return resp;
    }
}
