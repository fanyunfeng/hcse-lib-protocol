package com.hcse.protocol.d2.codec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.hcse.protocol.cache.util.CoderHelper;
import com.hcse.protocol.d2.message.D2RequestMessage;
import com.hcse.protocol.util.Constant;
import com.hcse.protocol.util.Encoder;

public class D2RequestMessageEncoder<T extends D2RequestMessage> implements MessageEncoder<T> {
    protected static final Logger logger = Logger.getLogger(D2RequestMessageEncoder.class);

    private static final String PACKAGE_MAGIC_NORMAL = "33334202";

    private static final int REQ_NORMAL_HEADER_LEN = 104;

    @Override
    public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {

        byte[] searchStrBuf = message.getSearchString().toUpperCase().getBytes(Constant.charsetName);

        int encodedSearchStringLength = searchStrBuf.length;

        int requestLength = (REQ_NORMAL_HEADER_LEN + encodedSearchStringLength);

        IoBuffer buf = IoBuffer.allocate(requestLength);

        buf.put(PACKAGE_MAGIC_NORMAL.getBytes());

        Encoder.encodeString(buf, message.getComIp(), 16);

        {
            String _userIp = CoderHelper.changeIp(message.getUserIp());
            if (StringUtils.isBlank(_userIp)) {
                logger.error("user ip is empty.");
                session.close(true);
                return;
            }

            Encoder.encodeString(buf, _userIp, 8);
        }

        Encoder.encodeLongString(buf, message.getBegin() >= 1 ? message.getBegin() : 0, 8);
        Encoder.encodeLongString(buf, message.getEnd(), 8);

        Encoder.encodeLongString(buf, message.getSort(), 8);
        Encoder.encodeLongString(buf, message.getStatisic(), 8);

        Encoder.encodeLongString(buf, message.isIndent() ? 1 : 0, 1);
        Encoder.encodeLongString(buf, message.isVip() ? 1 : 0, 1);

        {
            byte[] block = new byte[30];
            buf.put(block);
        }

        Encoder.encodeLongString(buf, encodedSearchStringLength, 8);

        buf.put(searchStrBuf);

        buf.flip();
        out.write(buf);
    }
}
