package com.hcse.d6.protocol.codec;

import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.hcse.d6.protocol.message.D6RequestMessage;
import com.hcse.d6.protocol.message.D6RequestMessageDoc;
import com.hcse.protocol.util.Constant;
import com.hcse.protocol.util.Encoder;

public class D6RequestMessageEncoder<T extends D6RequestMessage> implements MessageEncoder<T> {
    protected static final Logger logger = Logger.getLogger(D6RequestMessageEncoder.class);

    private static final String PACKAGE_MAGIC_NORMAL = "33334213";

    private static final int REQ_NORMAL_HEADER_LEN = 24;

    @Override
    public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {

        byte[] searchStrBuf = message.getSearchString().toUpperCase().getBytes(Constant.charsetName);

        int encodedSearchStringLength = searchStrBuf.length;

        int count = message.getDocsCount();
        int requestLength = (REQ_NORMAL_HEADER_LEN + encodedSearchStringLength + 21 * count);

        IoBuffer buf = IoBuffer.allocate(requestLength);

        buf.put(PACKAGE_MAGIC_NORMAL.getBytes());

        Encoder.encodeLongString(buf, message.getDocsCount(), 8);
        Encoder.encodeLongString(buf, encodedSearchStringLength, 8);

        // serialize doc list
        buf.order(ByteOrder.LITTLE_ENDIAN);

        for (D6RequestMessageDoc doc : message.getDocs()) {
            buf.putLong(doc.getMd5Lite());
        }

        for (D6RequestMessageDoc doc : message.getDocs()) {
            buf.put(doc.getMachineId());
        }

        for (D6RequestMessageDoc doc : message.getDocs()) {
            buf.putLong(doc.getWeight());
        }

        for (D6RequestMessageDoc doc : message.getDocs()) {
            buf.putInt(doc.getIndentCount());
        }

        // serialize search string
        buf.put(searchStrBuf);

        buf.flip();
        out.write(buf);
    }
}
