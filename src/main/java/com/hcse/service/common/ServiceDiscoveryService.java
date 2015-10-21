package com.hcse.service.common;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class ServiceDiscoveryService extends ServiceDiscovery {
    private ArrayList<ServiceDiscovery> instances = new ArrayList<ServiceDiscovery>();

    public ServiceDiscoveryService() {
        instances.add(new SimpleServiceDiscovery());
    }

    public void register(ServiceDiscoveryService disco) {
        int index = 0;
        for (ServiceDiscovery i : instances) {
            if (i.getWeight() < disco.getWeight()) {
                index++;
                continue;
            }
        }

        instances.add(index, disco);
    }

    public InetSocketAddress lookup(String address) throws MalformedURLException {
        InetSocketAddress inetSocketAddress;
        for (ServiceDiscovery i : instances) {
            inetSocketAddress = i.lookup(address);

            if (inetSocketAddress != null) {
                return inetSocketAddress;
            }
        }

        throw new MalformedURLException();
    }
}
