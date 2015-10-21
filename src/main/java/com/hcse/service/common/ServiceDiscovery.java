package com.hcse.service.common;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;

public abstract class ServiceDiscovery {
    private int weight;

    public ServiceDiscovery() {

    }

    public ServiceDiscovery(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public abstract InetSocketAddress lookup(String address) throws MalformedURLException;
}
