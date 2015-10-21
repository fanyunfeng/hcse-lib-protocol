package com.hcse.protocol.util;

import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;

public class Decoder {

    public static final int LONG_STRING_LENGTH = 8;

    public static int decodeLongString(IoBuffer in, CharsetDecoder decoder) throws Exception {
        String str = in.getString(LONG_STRING_LENGTH, decoder);
        int ret = Integer.parseInt(str.trim());

        return ret;
    }

    public static long String2MD5Lite(String str) {
        int v = 0;
        long md5Lite = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c >= '0' && c <= '9') {
                v = c - '0';
            } else if (c >= 'a' && c <= 'f') {
                v = c - 'a' + 10;
            } else if (c >= 'A' && c <= 'F') {
                v = c - 'A' + 10;
            }

            md5Lite <<= 4;
            md5Lite |= v;
        }

        return md5Lite;
    }

    private static final String HTML_RED_BEGIN_FLAG = "<font color=red>";
    private static final String HTML_RED_END_FLAG = "</font>";

    public static final String RED_BEGIN_FLAG = "\05";
    public static final String RED_END_FLAG = "\06";

    public static String addRedFlag(String str) throws Exception {
        str.replaceAll(RED_BEGIN_FLAG, HTML_RED_BEGIN_FLAG);
        str.replaceAll(RED_END_FLAG, HTML_RED_END_FLAG);

        return str;
    }
}
