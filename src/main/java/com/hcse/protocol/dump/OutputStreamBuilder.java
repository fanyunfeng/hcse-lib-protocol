package com.hcse.protocol.dump;

import java.io.OutputStream;

public interface OutputStreamBuilder {
    public OutputStream creatOutputStream(long tag);

    public void destory(OutputStream os);
}
