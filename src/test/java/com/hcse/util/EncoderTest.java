package com.hcse.util;

import junit.framework.Assert;

import org.junit.Test;

public class EncoderTest {

    @Test
    public void testMd5Lite() {
        String stringResult1 = Md5Lite.digestString("D_GNU_SOURCE -L/usr/local/lib");

        Assert.assertEquals(stringResult1, "C7C082C464FF6013");

        long longValue = Md5Lite.digestLong("D_GNU_SOURCE -L/usr/local/lib");

        String stringResult2 = Md5Lite.toString(longValue);

        Assert.assertEquals(stringResult1, stringResult2);
    }
}
