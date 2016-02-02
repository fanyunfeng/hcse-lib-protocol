package com.hcse.d6.protocol.message;

import java.nio.charset.CharsetDecoder;

import com.hcse.protocol.util.packet.BaseDoc;
import com.hcse.protocol.util.packet.FieldsMap;

public class D6ResponseMessageJsonDoc {
    private BaseDoc doc;

    public D6ResponseMessageJsonDoc() {
        doc = new BaseDoc();
    }

    public D6ResponseMessageJsonDoc(FieldsMap fileMap) {
        doc = new BaseDoc(fileMap);
    }

    public void dataProcess(CharsetDecoder decoder) throws Exception {
        doc.dataProcess(decoder);
    }

    public void setFieldValue(String name, String value) {
        doc.setFieldValue(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseDoc> T getDocument() {
        return (T) doc;
    }

    public void dump(int id) {
        doc.dump();
    }
}
