package com.hcse.cache.protocol.util;

import org.apache.log4j.Logger;

import com.hcse.protocol.util.Constant;

public class StatisicBuilder {

    protected static final Logger logger = Logger.getLogger(StatisicBuilder.class);

    public static String[] build(byte[] bytes) throws Exception {
        if (bytes == null || bytes.length == 0) {
            logger.error("bytes is null ");
            return null;
        }

        String str = new String(bytes, Constant.charsetName);
        String[] strs = str.split(";");
        return strs;
    }
}
