package com.hcse.protocol.d2.codec;

import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.hcse.protocol.d2.factory.D2ResponseMessageFactory;
import com.hcse.protocol.d2.message.D2ResponseMessage;
import com.hcse.protocol.util.Constant;
import com.hcse.protocol.util.Decoder;

public class D2ResponseMessageDecoder extends CumulativeProtocolDecoder {
    protected static final Logger logger = Logger.getLogger(D2ResponseMessageDecoder.class);

    public static final String PACK_MAGIC_V1 = "33334202";
    public static final String PACK_MAGIC_V2 = "73334202";

    public static final int PACK_MAGIC_LENGTH = 8;
    public static final int PACK_HEADER_LENGTH = 72 - 8;

    private int version = 2;
    private D2ResponseMessageFactory factory;

    public D2ResponseMessageDecoder(D2ResponseMessageFactory factory, int version) {
        this.factory = factory;
        this.version = version;
    }

    public D2ResponseMessageDecoder(D2ResponseMessageFactory factory) {
        this(factory, 2);
    }

    private D2ClientDecodeContext getContext(IoSession session) {
        D2ClientDecodeContext ctx = (D2ClientDecodeContext) session.getAttribute(D2ClientDecodeContext.class);

        if (ctx == null) {
            D2ResponseMessage response = factory.createResponseMessage();

            ctx = new D2ClientDecodeContext(Constant.newDecoder(), response);
            session.setAttribute(D2ClientDecodeContext.class, ctx);
        }

        return ctx;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        D2ClientDecodeContext ctx = getContext(session);
        D2ResponseMessage responseMessage = ctx.getResponseMessage();

        switch (ctx.getParseState()) {
        case 0: {
            if (in.remaining() < PACK_MAGIC_LENGTH) {
                return false;
            }

            String magic = in.getString(PACK_MAGIC_LENGTH, ctx.getDecoder());

            int headerLength = 0;

            if (PACK_MAGIC_V1.equals(magic)) {
                headerLength = PACK_HEADER_LENGTH;
            } else if (PACK_MAGIC_V2.equals(magic)) {
                headerLength = PACK_HEADER_LENGTH;
            } else {
                logger.error("Unknow header magic.");
                return true;
            }

            responseMessage.setHeaderLength(headerLength);
            ctx.setParseState(1);
        }
        case 1: {
            if (in.remaining() < responseMessage.getHeaderLength()) {
                return false;
            }

            headerDecode(ctx, responseMessage, session, in);
            ctx.setParseState(2);
        }
        case 2:
            if (in.remaining() < responseMessage.getBodyLength()) {
                return false;
            }

            ctx.setParseState(3);
            bodyDecode(responseMessage, session, in);

            responseMessage.dataProcess();
            out.write(responseMessage);
            return true;
        }

        return false;
    }

    private void headerDecode(D2ClientDecodeContext ctx, D2ResponseMessage responseMessage, IoSession session,
            IoBuffer in) throws Exception {

        responseMessage.setTotalCount(Decoder.decodeLongString(in, ctx.getDecoder()));
        responseMessage.setRealCount(Decoder.decodeLongString(in, ctx.getDecoder()));
        responseMessage.setMmtCount(Decoder.decodeLongString(in, ctx.getDecoder()));
        responseMessage.setDocsCount(Decoder.decodeLongString(in, ctx.getDecoder()));

        responseMessage.setMachineId(Decoder.decodeLongString(in, ctx.getDecoder()));
        responseMessage.setIndent(Decoder.decodeLongString(in, ctx.getDecoder()));
        responseMessage.setDocsLength(Decoder.decodeLongString(in, ctx.getDecoder()));
        responseMessage.setClassLength(Decoder.decodeLongString(in, ctx.getDecoder()));

        responseMessage.setBodyLength(responseMessage.getDocsLength() + responseMessage.getClassLength());
    }

    private void bodyDecode(D2ResponseMessage responseMessage, IoSession session, IoBuffer in) throws Exception {
        in.order(ByteOrder.LITTLE_ENDIAN);

        int docsCount = responseMessage.getDocsCount();
        // md5Lite
        for (int i = 0; i < docsCount; i++) {
            responseMessage.getDocById(i).setMd5Lite(in.getLong());
        }

        for (int i = 0; i < docsCount; i++) {
            responseMessage.getDocById(i).setWeight(in.getLong());
        }

        for (int i = 0; i < docsCount; i++) {
            responseMessage.getDocById(i).setIndentValue(in.getInt());
        }

        for (int i = 0; i < docsCount; i++) {
            responseMessage.getDocById(i).setIndentPage(in.getInt());
        }

        for (int i = 0; i < docsCount; i++) {
            responseMessage.getDocById(i).setIndentCount(in.getInt());
        }

        if (version == 2) {
            for (int i = 0; i < docsCount; i++) {
                responseMessage.getDocById(i).setPo(in.getInt());
            }

            for (int i = 0; i < docsCount; i++) {
                responseMessage.getDocById(i).setPh(in.getInt());
            }
        }

        for (int i = 0; i < docsCount; i++) {
            responseMessage.getDocById(i).dataProcess();
        }

        bodyClassInfo(responseMessage, session, in);
    }

    private void bodyClassInfo(D2ResponseMessage responseMessage, IoSession session, IoBuffer in) throws Exception {

    }
}
