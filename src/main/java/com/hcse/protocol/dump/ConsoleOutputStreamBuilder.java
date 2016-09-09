package com.hcse.protocol.dump;

import java.io.OutputStream;

public class ConsoleOutputStreamBuilder implements OutputStreamBuilder {

    @Override
    public OutputStream creatOutputStream(long tag) {
        return System.out;
    }

    @Override
    public void destory(OutputStream os) {

    }
}
