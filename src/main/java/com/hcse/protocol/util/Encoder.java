package com.hcse.protocol.util;

import org.apache.mina.core.buffer.IoBuffer;

public class Encoder {
    public static void encodeString(IoBuffer in, String str, int length) {
        if (str.length() >= length) {
            in.put(str.getBytes(), 0, length);
        } else {
            byte[] array = str.getBytes();

            in.put(array);

            in.fill((byte) 0x20, length - array.length);
        }
    }

    public static void encodeLongString(IoBuffer in, long value, int length) {
        String str = String.format("%d", value);

        byte[] array = str.getBytes();

        in.put(array);

        in.fill((byte) 0x20, length - array.length);
    }
}
