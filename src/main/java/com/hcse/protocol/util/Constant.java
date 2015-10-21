package com.hcse.protocol.util;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Constant {
    public static final String charsetName = "GBK";

    public static final Charset charset = Charset.forName("GBK");

    public static CharsetDecoder newDecoder() {

        return charset.newDecoder();
    }

    public static CharsetEncoder newEncoder() {
        return charset.newEncoder();
    }
}
