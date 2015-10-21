package com.hcse.service.common;

public class URL {
    String protocol;
    String host;
    int port;

    String url;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public URL(String url) {
        this.url = url;
        init(url);
    }

    private void init(String url) {
        String[] array = url.split("://");

        if (array == null) {
            return;
        }

        if (array.length >= 2) {
            protocol = array[0];
            initHostAndPort(array[1]);
        } else {
            initHostAndPort(array[0]);
        }
    }

    private void initHostAndPort(String str) {
        String[] array = str.split(":");

        if (array == null) {
            return;
        }

        if (array.length >= 2) {
            host = array[0];
            port = Integer.parseInt(array[1]);
        } else {
            host = array[0];
        }
    }
}
