package com.hcse.service.common;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.util.HashMap;

public class SimpleServiceDiscovery extends ServiceDiscovery {
    class ServiceInfo {
        private String scheme;
        private int port;

        ServiceInfo(String scheme, int port) {
            this.scheme = scheme;
            this.port = port;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    };

    private HashMap<String, ServiceInfo> protocolList = new HashMap<String, ServiceInfo>();

    public SimpleServiceDiscovery() {
        super(100);

        registProtocol("cache", 5555);
        registProtocol("index", 3000);
        registProtocol("data", 3000);
    }

    private void registProtocol(String scheme, int port) {
        ServiceInfo info = new ServiceInfo(scheme, port);
        protocolList.put(info.getScheme(), info);
    }

    int getDefaultPort(String protocol) {

        ServiceInfo info = protocolList.get(protocol);

        if (info == null) {
            return 0;
        }
        return info.getPort();
    }

    public InetSocketAddress lookup(String address) throws MalformedURLException {
        URL url = new URL(address);

        String protocol = url.getProtocol();

        ServiceInfo info = protocolList.get(protocol);

        if (info != null) {
            String host = url.getHost();

            int port = url.getPort();

            if (port == 0) {
                port = info.getPort();
            }

            return new InetSocketAddress(host, port);
        }

        return null;
    }
}
