package com.hcse.d6.protocol.codec;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.hcse.d6.protocol.factory.D6ResponseMessageFactory;
import com.hcse.d6.protocol.message.D6ResponseMessage;
import com.hcse.d6.protocol.message.D6ResponseMessageClientDoc;
import com.hcse.protocol.util.Decoder;

public class D6ResponseMessageDecoder extends CumulativeProtocolDecoder {
    protected static final Logger logger = Logger.getLogger(D6ResponseMessageDecoder.class);

    final Charset charset = Charset.forName("GBK");

    public static final String PACK_MAGIC_V1 = "33334213";

    public static final int PACK_MAGIC_LENGTH = 8;
    public static final int PACK_HEADER_LENGTH = 24 - 8;

    private D6ResponseMessageFactory factory;

    public D6ResponseMessageDecoder(D6ResponseMessageFactory factory) {
        this.factory = factory;
    }

    private D6ClientDecodeContext getContext(IoSession session) {
        D6ClientDecodeContext ctx = (D6ClientDecodeContext) session.getAttribute(D6ClientDecodeContext.class);

        if (ctx == null) {
            D6ResponseMessage response = factory.createResponseMessage();

            ctx = new D6ClientDecodeContext(charset.newDecoder(), response);
            session.setAttribute(D6ClientDecodeContext.class, ctx);
        }

        return ctx;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        D6ClientDecodeContext ctx = getContext(session);
        D6ResponseMessage responseMessage = ctx.getResponseMessage();

        switch (ctx.getParseState()) {
        case 0: {
            if (in.remaining() < PACK_MAGIC_LENGTH) {
                return false;
            }

            String magic = in.getString(PACK_MAGIC_LENGTH, ctx.getDecoder());

            int headerLength = 0;

            if (PACK_MAGIC_V1.equals(magic)) {
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
            bodyDecode(responseMessage, session, in, ctx.getDecoder());

            responseMessage.dataProcess();
            out.write(responseMessage);
            return true;
        }

        return false;
    }

    private void headerDecode(D6ClientDecodeContext ctx, D6ResponseMessage responseMessage, IoSession session,
            IoBuffer in) throws Exception {

        responseMessage.setDocsCount(Decoder.decodeLongString(in, ctx.getDecoder()));
        responseMessage.setDocsLength(Decoder.decodeLongString(in, ctx.getDecoder()));
    }

    private void bodyDecode(D6ResponseMessage responseMessage, IoSession session, IoBuffer in, CharsetDecoder decoder)
            throws Exception {
        in.order(ByteOrder.LITTLE_ENDIAN);

        int[] beginDocs = new int[32];

        for (int i = 0; i < 32; i++) {
            beginDocs[i] = in.getInt();
        }

        bodyDocDecode(responseMessage, session, in, decoder, beginDocs);
    }

    private void bodyDocDecode(D6ResponseMessage responseMessage, IoSession session, IoBuffer in,
            CharsetDecoder decoder, int[] beginDocs) throws Exception {

        for (int i = 0; i < responseMessage.getDocsCount() - 1; i++) {
            D6ResponseMessageClientDoc doc = factory.createResponseMessageDoc();

            int size = beginDocs[i + 1] - beginDocs[i];

            IoBuffer buf = in.getSlice(size);

            doc.setSize(size);
            doc.setBuffer(buf);

            responseMessage.appendDoc(doc);
            doc.dataProcess(decoder);
        }

        int pos = in.position();
        int limit = in.limit();

        byte b = in.get(limit - 1);

        if (b != 0x07) {
            in.clear();
            in.position(limit);

            in.put((byte) (0x07));
            in.flip();
            in.position(pos);
        }

        limit = in.limit();
        int size = limit - pos;
        IoBuffer buf = in.getSlice(limit - pos);

        D6ResponseMessageClientDoc doc = factory.createResponseMessageDoc();

        doc.setSize(size);
        doc.setBuffer(buf);

        responseMessage.appendDoc(doc);
        doc.dataProcess(decoder);
    }
}
