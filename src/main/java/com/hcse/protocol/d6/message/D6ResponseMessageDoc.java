package com.hcse.protocol.d6.message;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.hcse.protocol.BasePacket;

public class D6ResponseMessageDoc extends BasePacket {
    protected static final Logger logger = Logger.getLogger(D6ResponseMessageDoc.class);

    private IoBuffer buffer;
    private int size;

    public IoBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(IoBuffer buffer) {
        this.buffer = buffer;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void dump(int id) {
        logger.info(String.format("%d\t size:%d dump:%s", id, getSize(), getBuffer().getHexDump()));
    }
}
