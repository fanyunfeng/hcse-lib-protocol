package com.hcse.d6.service;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcse.d6.protocol.codec.D6ClientCoderFactory;
import com.hcse.d6.protocol.factory.D6ResponseMessageFactory;
import com.hcse.d6.protocol.message.D6RequestMessage;
import com.hcse.d6.protocol.message.D6ResponseMessage;
import com.hcse.service.common.ServiceDiscoveryService;

@Service
public class DataServiceImpl implements DataService {
    protected final Logger logger = Logger.getLogger(DataServiceImpl.class);

    @Autowired
    ServiceDiscoveryService serviceDiscovery;

    private int maxRetryTimes;

    public DataServiceImpl() {
        maxRetryTimes = 2;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public D6ResponseMessage search(D6RequestMessage request, D6ResponseMessageFactory factory)
            throws MalformedURLException {

        logger.info("request to cache:" + request);

        int socketTimeout = 600;

        D6ResponseMessage resp = null;

        int retryTimes = 1;
        while (retryTimes < maxRetryTimes) {
            NioSocketConnector connector = new NioSocketConnector();

            // connector.getFilterChain().addLast("logger", new
            // LoggingFilter());
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new D6ClientCoderFactory(factory)));
            connector.setConnectTimeoutMillis(socketTimeout);
            connector.getSessionConfig().setUseReadOperation(true);

            ConnectFuture cf = connector.connect(serviceDiscovery.lookup(request.getServiceAddress()));

            cf.awaitUninterruptibly();
            try {
                if (cf.isConnected()) {
                    cf.getSession().write(request);

                    ReadFuture readFuture = cf.getSession().read();

                    if (readFuture.awaitUninterruptibly(socketTimeout, TimeUnit.SECONDS)) {
                        resp = (D6ResponseMessage) readFuture.getMessage();
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
