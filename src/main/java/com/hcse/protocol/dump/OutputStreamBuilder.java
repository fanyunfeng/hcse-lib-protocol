package com.hcse.protocol.dump;

import java.io.OutputStream;

public interface OutputStreamBuilder {
    public OutputStream creatOutputStream();

    public void destory(OutputStream os);
}
