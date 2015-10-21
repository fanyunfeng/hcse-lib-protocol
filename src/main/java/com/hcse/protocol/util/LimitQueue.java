package com.hcse.protocol.util;

import org.apache.mina.core.buffer.IoBuffer;

public class LimitQueue {
    private int size = 0;
    private int array[];

    public LimitQueue(int size) {
        array = new int[size];
    }

    public void pushLimit(IoBuffer in, int length) {
        array[size++] = in.limit();
        in.limit(in.position() + length);
    }

    public void popLimit(IoBuffer in) {
        in.limit(array[--size]);
    }

}
