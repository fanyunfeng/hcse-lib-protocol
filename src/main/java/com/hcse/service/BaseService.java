package com.hcse.service;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Autowired;

import com.hcse.protocol.BaseRequest;
import com.hcse.protocol.util.LoggingFilter;
import com.hcse.service.common.ServiceDiscoveryService;
import com.hcse.service.d6.DataServiceImpl;

public class BaseService<RequestMessage extends BaseRequest, ResponseMessage, ResponseCodecFactory extends ProtocolCodecFactory> {
    protected final Logger logger = Logger.getLogger(DataServiceImpl.class);

    private ServiceDiscoveryService serviceDiscovery;

    @Autowired
    public void setServiceDiscoveryService(ServiceDiscoveryService service) {
        serviceDiscovery = service;
    }

    private NioSocketConnector connector;

    private int requestTimeout = 600;
    private int connectTimeout = 600;
    private int maxTryTimes = 1;

    public int getMaxTryTimes() {
        return maxTryTimes;
    }

    public void setMaxTryTimes(int maxTryTimes) {
        this.maxTryTimes = maxTryTimes;
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

    private NioSocketConnector openConnector(ResponseCodecFactory factory) {
        NioSocketConnector connector = new NioSocketConnector();

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(factory));
        connector.setConnectTimeoutMillis(connectTimeout);
        connector.getSessionConfig().setUseReadOperation(true);

        return connector;
    }

    private void closeConnector(NioSocketConnector connector) {
        if (connector != null) {
            connector.dispose();
            if (logger.isDebugEnabled()) {
                logger.debug("connector closed.");
            }
        }
    }

    public void open(ResponseCodecFactory factory) {
        this.connector = openConnector(factory);
    }

    public void close() {
        closeConnector(connector);
    }

    @SuppressWarnings("unchecked")
    private ResponseMessage search(RequestMessage request, NioSocketConnector connector) throws MalformedURLException,
            ServiceException {

        ResponseMessage resp = null;
        IoSession session = null;

        int tryTimes = 0;

        while (true) {
            ConnectFuture cf = connector.connect(serviceDiscovery.lookup(request.getServiceAddress()));

            cf.awaitUninterruptibly();
            try {
                if (cf.isConnected()) {
                    session = cf.getSession();
                    session.write(request);

                    session.suspendWrite();

                    ReadFuture readFuture = session.read();

                    if (readFuture.awaitUninterruptibly(requestTimeout, TimeUnit.MILLISECONDS)) {
                        resp = (ResponseMessage) readFuture.getMessage();
                        if (resp != null) {
                            break;
                        } else {
                            tryTimes++;
                            if (tryTimes >= maxTryTimes) {
                                throw new RequestTimeout();
                            } else {
                                logger.error("response is null.");
                            }
                        }
                    }
                } else {
                    tryTimes++;
                    if (tryTimes >= maxTryTimes) {
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

        return resp;
    }

    public ResponseMessage search(RequestMessage request, ResponseCodecFactory factory) throws MalformedURLException,
            ServiceException {

        if (factory == null) {
            return search(request, connector);
        }

        NioSocketConnector connector = openConnector(factory);

        ResponseMessage response = search(request, connector);
        closeConnector(connector);

        return response;
    }
}
