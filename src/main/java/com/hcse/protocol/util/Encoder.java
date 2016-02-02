package com.hcse.protocol.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    static char[] charMap = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String digestMd5Lite(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest.update(content.getBytes(Constant.charset));

            int hight, low;
            byte[] buf = digest.digest();

            StringBuilder sb = new StringBuilder(32);
            for (int i = 7; i >= 0; i--) {

                hight = buf[i] & 0xF0;
                hight >>>= 4;
                low = buf[i] & 0x0F;

                sb.append(charMap[hight]);
                sb.append(charMap[low]);
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {

            return null;
        }
    }
}
