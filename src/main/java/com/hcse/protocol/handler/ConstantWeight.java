package com.hcse.protocol.handler;

import com.hcse.protocol.BaseResponseDoc;

public class ConstantWeight implements DocHandler {
    private long now;

    public ConstantWeight() {
        now = System.currentTimeMillis();

        now /= 1000;

        now /= 300;
    }

    public void process(BaseResponseDoc doc) {
        doc.setWeight(doc.getWeight() - now);
    }
}
