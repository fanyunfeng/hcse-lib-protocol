package com.hcse.d6.protocol.message;

import java.nio.charset.CharsetDecoder;

import com.hcse.cache.protocol.message.CacheResponseMessageDoc;
import com.hcse.protocol.util.codec.DocumentDecoder;
import com.hcse.protocol.util.packet.FieldsMap;

public class D6ResponseMessageClientDoc extends D6ResponseMessageDoc {
    private CacheResponseMessageDoc doc;

    public D6ResponseMessageClientDoc() {
        doc = new CacheResponseMessageDoc();
    }

    public D6ResponseMessageClientDoc(FieldsMap fileMap) {
        doc = new CacheResponseMessageDoc(fileMap);
    }

    public void dataProcess(CharsetDecoder decoder) throws Exception {
        DocumentDecoder.decodeResponseMessageDoc(this.getBuffer(), doc, decoder);
    }

    public void dump(int id) {
        doc.dump();
    }
}
