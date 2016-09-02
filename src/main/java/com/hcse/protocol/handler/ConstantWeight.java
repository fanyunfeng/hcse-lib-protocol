package com.hcse.protocol.handler;

import com.hcse.protocol.BaseResponseDoc;

public class ConstantWeight {
    private long now;

    public ConstantWeight() {
        now = System.currentTimeMillis();

        now /= 1000;

        now /= 300;
    }

    void process(BaseResponseDoc doc) {
        doc.setWeight(doc.getWeight() - now);
    }
}
