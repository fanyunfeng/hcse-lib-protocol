package com.hcse.d2.service;

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

import com.hcse.d2.protocol.codec.D2ClientCoderFactory;
import com.hcse.d2.protocol.factory.D2ResponseMessageFactory;
import com.hcse.d2.protocol.message.D2RequestMessage;
import com.hcse.d2.protocol.message.D2ResponseMessage;
import com.hcse.protocol.util.LoggingFilter;
import com.hcse.service.common.ServiceDiscoveryService;

@Service
public class IndexServiceImpl implements IndexService {
    protected final Logger logger = Logger.getLogger(IndexServiceImpl.class);

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

    private NioSocketConnector openConnector(D2ResponseMessageFactory factory) {
        NioSocketConnector connector = new NioSocketConnector();

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new D2ClientCoderFactory(factory)));
        connector.setConnectTimeoutMillis(connectTimeout);
        connector.getSessionConfig().setUseReadOperation(true);

        return connector;
    }

    private void closeConnector(NioSocketConnector connector) {
        if (connector != null) {
            if (!connector.isDisposed()) {
                connector.dispose();
                logger.info("connector closed.");
            }
        }
    }

    public void open(D2ResponseMessageFactory factory) {
        this.connector = openConnector(factory);
    }

    public void close() {
        closeConnector(connector);
    }

    @Override
    public D2ResponseMessage search(D2RequestMessage request, D2ResponseMessageFactory factory)
            throws MalformedURLException {

        NioSocketConnector connector = this.connector;

        D2ResponseMessage resp = null;
        IoSession session = null;

        int retryTimes = 1;

        if (factory != null) {
            connector = openConnector(factory);
        }

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
                        resp = (D2ResponseMessage) readFuture.getMessage();
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
                logger.error("exception:", e);
                retryTimes++;
            } finally {
                if (session != null) {
                    session.close(true);
                    logger.info("session closed.");
                }
            }
        }

        if (connector != null && connector != this.connector) {
            if (!connector.isDisposed()) {
                connector.dispose();
                logger.info("connector closed.");
            }
        }

        return resp;
    }
}
