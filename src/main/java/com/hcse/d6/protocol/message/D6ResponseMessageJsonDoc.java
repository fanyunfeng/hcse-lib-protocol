package com.hcse.d6.protocol.message;

import java.nio.charset.CharsetDecoder;

import com.hcse.cache.protocol.message.CacheResponseMessageDoc;
import com.hcse.protocol.util.packet.FieldsMap;

public class D6ResponseMessageJsonDoc {
    private CacheResponseMessageDoc doc;

    public D6ResponseMessageJsonDoc() {
        doc = new CacheResponseMessageDoc();
    }

    public D6ResponseMessageJsonDoc(FieldsMap fileMap) {
        doc = new CacheResponseMessageDoc(fileMap);
    }

    public void dataProcess(CharsetDecoder decoder) throws Exception {
        doc.dataProcess(decoder);
    }

    public void setFieldValue(String name, String value) {
        doc.setFieldValue(name, value);
    }

    public Object getDoc() {
        return doc;
    }

    public void dump(int id) {
        doc.dump();
    }
}
