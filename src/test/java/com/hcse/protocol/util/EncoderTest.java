package com.hcse.protocol.util;

import junit.framework.Assert;

import org.junit.Test;


public class EncoderTest {

    @Test
    public void testMd5Lite() {
        String result = Encoder.digestMd5Lite("D_GNU_SOURCE -L/usr/local/lib");

        Assert.assertEquals(result, "C7C082C464FF6013");
    }
}
