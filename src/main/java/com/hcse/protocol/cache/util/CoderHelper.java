package com.hcse.protocol.cache.util;

import org.apache.commons.lang.StringUtils;

public class CoderHelper {
    public static String changeIp(String ip) {
        StringBuffer sb = new StringBuffer();
        String[] ips = ip.split("\\.");
        if (ips == null || ips.length != 4) {
            return null;
        }
        int num = 0;
        for (String _ip : ips) {
            num = Integer.parseInt(_ip);
            sb.append(String.format("%02X", num));
        }
        return sb.toString();
    }

    public static String formatStr(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        str = str.replace("&#160;", " ").replace("&#60;", "<").replace("&#62;", ">").replace("&#38;", "&")
                .replace("&#34;", "\"").replace("&#39;", "\'").replace("&#215;", "×").replace("&#247;", "÷");
        return str;
    }
}
