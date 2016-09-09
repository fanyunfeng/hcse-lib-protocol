package com.hcse.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.hcse.protocol.util.Constant;

public class Md5Lite {
    static char[] charMap = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String digestString(String content) {
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

    public static long digestLong(String content) {
        try {
            long ret = 0;
            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest.update(content.getBytes(Constant.charset));

            byte[] buf = digest.digest();

            for (int i = 7; i >= 0; i--) {
                ret <<= 8;
                ret |= buf[i] & 0xFF;
            }

            return ret;

        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
    }

    public static String toString(long v) {
        String ret = Long.toHexString(v).toUpperCase();

        if (ret.length() == 16) {
            return ret;
        }

        StringBuilder sb = new StringBuilder(16);

        for (int i = ret.length(); i < 16; i++) {
            sb.append('0');
        }

        sb.append(ret);

        return sb.toString();
    }
}
