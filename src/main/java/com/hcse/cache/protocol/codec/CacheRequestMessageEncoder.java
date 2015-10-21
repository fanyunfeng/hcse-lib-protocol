package com.hcse.cache.protocol.codec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.hcse.cache.protocol.message.CacheRequestMessage;
import com.hcse.cache.protocol.util.CoderHelper;
import com.hcse.protocol.util.Constant;
import com.hcse.protocol.util.Encoder;

public class CacheRequestMessageEncoder<T extends CacheRequestMessage> implements MessageEncoder<T> {

    protected static final Logger logger = Logger.getLogger(CacheRequestMessageEncoder.class);

    private static final String PACKAGE_MAGIC_NORMAL = "3b06^2k6";

    public static final int REQ_COMMON_HEADER_LEN = 114;

    @Override
    public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {

        byte[] searchStrBuf = message.getSearchString().toUpperCase().getBytes(Constant.charsetName);

        int encodedSearchStringLength = searchStrBuf.length;

        int requestLength = (REQ_COMMON_HEADER_LEN + encodedSearchStringLength);

        IoBuffer buf = IoBuffer.allocate(requestLength);

        buf.put(PACKAGE_MAGIC_NORMAL.getBytes());

        Encoder.encodeString(buf, message.getComIp(), 16);

        String userIp = CoderHelper.changeIp(message.getUserIp());
        if (StringUtils.isBlank(userIp)) {
            logger.error("user ip is empty.");
            session.close(true);
            return;
        }

        Encoder.encodeString(buf, userIp, 8);

        Encoder.encodeLongString(buf, message.getBeginItem() > 1 ? message.getBeginItem() : 1, 8);
        Encoder.encodeLongString(buf, message.getEndItem(), 8);

        Encoder.encodeLongString(buf, message.getSortMethod(), 8);

        Encoder.encodeLongString(buf, message.getStatisic(), 8);

        Encoder.encodeLongString(buf, message.getIndent(), 1);
        Encoder.encodeLongString(buf, message.getFreshFlag(), 1);
        Encoder.encodeLongString(buf, message.getSearchSign(), 1);
        Encoder.encodeLongString(buf, message.getSearchSign(), 1);

        if (StringUtils.isBlank(message.getAssortNum())) {
            buf.put(new byte[24]);
        } else {
            Encoder.encodeString(buf, message.getAssortNum(), 24);
        }

        Encoder.encodeString(buf, message.getSc(), 3);

        if (StringUtils.isBlank(message.getTradeNum())) {
            buf.put(new byte[3]);
        } else {
            Encoder.encodeString(buf, message.getTradeNum(), 3);
        }

        Encoder.encodeLongString(buf, message.getSearchFlag(), 8);

        Encoder.encodeLongString(buf, encodedSearchStringLength, 8);

        buf.put(searchStrBuf);

        buf.flip();
        out.write(buf);
    }
}
