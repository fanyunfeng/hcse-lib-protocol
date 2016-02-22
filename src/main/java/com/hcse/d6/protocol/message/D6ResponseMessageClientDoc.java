package com.hcse.d6.protocol.message;

import java.nio.ByteOrder;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.hcse.protocol.util.codec.DocumentDecoder;
import com.hcse.protocol.util.packet.BaseDoc;
import com.hcse.protocol.util.packet.FieldsMap;

public class D6ResponseMessageClientDoc extends D6ResponseMessageDoc {
    protected BaseDoc doc;

    public D6ResponseMessageClientDoc() {
        doc = new BaseDoc();
    }

    public D6ResponseMessageClientDoc(FieldsMap fileMap) {
        doc = new BaseDoc(fileMap);
    }

    public void dataProcess(CharsetDecoder decoder) throws Exception {
        IoBuffer buf = this.getBuffer();

        buf.order(ByteOrder.LITTLE_ENDIAN);

        DocumentDecoder.decodeResponseMessageDoc(buf, doc, decoder);
        doc.dataProcess(decoder);
    }

    public void setFieldValue(String name, long value) {
        if (name.equals("RESULTPOWER")) {
            doc.setWeight(value);

            return;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseDoc> T getDocument() {
        return (T) doc;
    }

    public void setFieldValue(String name, String value) {

        doc.setFieldValue(name, value);
    }

    public void dump(int id) {
        doc.dump();
    }
}
