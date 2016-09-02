package com.hcse.protocol.util.codec;

import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.hcse.protocol.cache.codec.CacheResponseMessageDecoder;
import com.hcse.protocol.util.packet.BaseDoc;

public class DocumentDecoder {
    public static BaseDoc decodeResponseMessageDoc(IoBuffer in, BaseDoc doc,
            CharsetDecoder decoder) throws Exception {

        parseHeader(in, doc);

        byte b;
        int length = 0;

        int index = 0;
        int position = in.position();

        while (in.hasRemaining()) {
            b = in.get();

            switch (b) {
            case CacheResponseMessageDecoder.FIELD_SPLIT_FLAG:
                in.position(position);
                if (length > 0) {
                    doc.setFieldValue(index, in.getString(length, decoder));
                } else {
                    doc.setFieldValue(index, "");
                }

                in.skip(1);

                index++;
                position = in.position();
                length = 0;
                break;
            case CacheResponseMessageDecoder.DOC_SPLIT_FLAG:
                in.position(position);
                if (length > 0) {
                    doc.setFieldValue(index, in.getString(length, decoder));
                } else {
                    doc.setFieldValue(index, "");
                }

                in.skip(1);

                return doc;
            default:
                length++;
            }
        }

        return doc;
    }

    private static void parseHeader(IoBuffer in, BaseDoc doc) throws Exception {
        // (Md5值(8)+ 0x08 + 权重(8) + 0x08 + 缩进(4) + 0x08)
        doc.setMd5Lite(in.getLong());
        // 0x08
        in.skip(1);

        doc.setWeight(in.getLong());

        // 0x08
        in.skip(1);

        doc.setGroupCount(in.getInt());

        // 0x08
        in.skip(1);
    }
}
