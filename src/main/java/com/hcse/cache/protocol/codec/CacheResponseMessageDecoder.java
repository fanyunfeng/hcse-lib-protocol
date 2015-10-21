package com.hcse.cache.protocol.codec;

import java.nio.ByteOrder;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.hcse.cache.protocol.factory.CacheResponseMessageFactory;
import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.cache.protocol.message.CacheResponseMessageDoc;
import com.hcse.protocol.util.Constant;
import com.hcse.protocol.util.Decoder;
import com.hcse.protocol.util.LimitQueue;
import com.hcse.protocol.util.codec.DocumentDecoder;
import com.hcse.protocol.util.codec.ParameterDecoder;

public class CacheResponseMessageDecoder extends CumulativeProtocolDecoder {
    public static final int PACK_MARK_LENGTH = 8;
    public static final String PACK_MAGIC_V1 = "3b07^2k6";
    public static final String PACK_MAGIC_V2 = "3b06^2k6";

    public static final byte DOC_SPLIT_FLAG = 0x07;
    public static final byte FIELD_SPLIT_FLAG = 0x08;

    protected static final Logger logger = Logger.getLogger(CacheResponseMessageDecoder.class);

    private CacheResponseMessageFactory factory;

    public CacheResponseMessageDecoder(CacheResponseMessageFactory factory) {
        this.factory = factory;
    }

    private CacheClientDecodeContext getContext(IoSession session) {
        CacheClientDecodeContext ctx = (CacheClientDecodeContext) session.getAttribute(CacheClientDecodeContext.class);

        if (ctx == null) {
            CacheResponseMessage response = factory.createResponseMessage();

            ctx = new CacheClientDecodeContext(Constant.newDecoder(), response);
            session.setAttribute(CacheClientDecodeContext.class, ctx);
        }

        return ctx;
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        CacheClientDecodeContext ctx = getContext(session);
        CacheResponseMessage responseMessage = ctx.getResponseMessage();

        switch (ctx.getParseState()) {
        case 0: {
            if (in.remaining() < PACK_MARK_LENGTH) {
                return false;
            }

            String flag = in.getString(PACK_MARK_LENGTH, ctx.getDecoder());

            int headerLength = 0;
            if (PACK_MAGIC_V1.equals(flag)) {
                headerLength = 96 - 8;
            } else if (PACK_MAGIC_V2.equals(flag)) {
                headerLength = 104 - 8;
            } else {
                logger.error("Unknow header magic.");
                return true;
            }

            responseMessage.setMessageFlag(flag);
            responseMessage.setHeaderLength(headerLength);
            ctx.setParseState(1);
        }
        case 1: {
            if (in.remaining() < responseMessage.getHeaderLength()) {
                return false;
            }

            headerDecoder(ctx, responseMessage, session, in);
            ctx.setParseState(2);
        }
        case 2:
            if (in.remaining() < responseMessage.getContentLength()) {
                return false;
            }

            ctx.setParseState(3);
            bodyDecoder(ctx, responseMessage, session, in);

            responseMessage.dataProcess();
            out.write(responseMessage);
            return true;
        }

        return false;
    }

    private void headerDecoder(CacheClientDecodeContext ctx, CacheResponseMessage responseMessage, IoSession session,
            IoBuffer in) throws Exception {

        CharsetDecoder decoder = ctx.getDecoder();

        responseMessage.setTotalCount(Decoder.decodeLongString(in, decoder));
        responseMessage.setRealCount(Decoder.decodeLongString(in, decoder));
        responseMessage.setMmtCount(Decoder.decodeLongString(in, decoder));
        responseMessage.setResultNum(Decoder.decodeLongString(in, decoder));

        int size = 0;
        size += responseMessage.setReturnLength(Decoder.decodeLongString(in, decoder));
        size += responseMessage.setStatisLength(Decoder.decodeLongString(in, decoder));
        size += responseMessage.setTipLength(Decoder.decodeLongString(in, decoder));
        size += responseMessage.setSaurusLength(Decoder.decodeLongString(in, decoder));
        size += responseMessage.setParameterLength(Decoder.decodeLongString(in, decoder));
        size += responseMessage.setTopkeywordLength(Decoder.decodeLongString(in, decoder));
        size += responseMessage.setClassifyInfoLength(Decoder.decodeLongString(in, decoder));

        if (PACK_MAGIC_V2.equals(responseMessage.getMessageFlag())) {
            size += responseMessage.setCutWordLength(Decoder.decodeLongString(in, decoder));
        }

        responseMessage.setContentLength(size);
    }

    // 内容解析
    private void bodyDecoder(CacheClientDecodeContext ctx, CacheResponseMessage responseMessage, IoSession session,
            IoBuffer in) throws Exception {

        CharsetDecoder decoder = ctx.getDecoder();

        LimitQueue queue = new LimitQueue(20);
        in.order(ByteOrder.LITTLE_ENDIAN);

        String str = null;

        // 文档内容的解析
        if (responseMessage.getReturnLength() > 0) {
            decodeMessageDocs(responseMessage, session, in, decoder, responseMessage.getReturnLength(), queue);
        }

        // 统计结果解析
        if (responseMessage.getStatisticLength() > 0) {
            decodeStatistic(responseMessage, session, in, decoder, responseMessage.getStatisticLength(), queue);
        }

        // 提示词解析
        if (responseMessage.getTipLength() > 0) {
            str = in.getString(responseMessage.getTipLength(), decoder);
            responseMessage.setTipWord(str.trim());
        }

        // 同义词
        if (responseMessage.getSaurusLength() > 0) {
            str = in.getString(responseMessage.getSaurusLength(), decoder);
            responseMessage.setSaurusWord(str);
        }

        // 参数
        if (responseMessage.getParameterLength() > 0) {
            decodeParameters(responseMessage, session, in, decoder, responseMessage.getParameterLength(), queue);
        }

        // 匹配关键词
        if (responseMessage.getTopKeywordLength() > 0) {
            str = in.getString(responseMessage.getTopKeywordLength(), decoder);
            responseMessage.setTopKeyword(str.trim());
        }

        // 分类
        if (responseMessage.getClassifyInfoLength() > 0) {
            str = in.getString(responseMessage.getClassifyInfoLength(), decoder);
            responseMessage.setClassCode(str.trim());
        }

        // 切词
        if (responseMessage.getCutWordLength() > 0) {
            str = in.getString(responseMessage.getCutWordLength(), decoder);

            if (StringUtils.isNotBlank(str)) {
                responseMessage.setCutWords(str.split("\\s"));
            }
        }
    }

    private void decodeParameters(CacheResponseMessage responseMessage, IoSession session, IoBuffer in,
            CharsetDecoder decoder, int length, LimitQueue queue) throws Exception {

        int size = Decoder.decodeLongString(in, decoder);

        if (size > 0) {
            responseMessage.setSelectedParameters(ParameterDecoder.decodeParameter(responseMessage, session, in,
                    decoder, length, queue));
        }

        length = length - size - 8;
        responseMessage.setUnselectedParameters(ParameterDecoder.decodeParameter(responseMessage, session, in, decoder,
                length, queue));
    }

    private CacheResponseMessage decodeStatistic(CacheResponseMessage responseMessage, IoSession session, IoBuffer in,
            CharsetDecoder decoder, int length, LimitQueue queue) throws Exception {
        Map<Integer, String[]> statisInfos = new HashMap<Integer, String[]>();

        queue.pushLimit(in, length);
        int index = 1;
        String[] value;

        byte b;
        int position = in.position();
        int size = 0;

        while (in.hasRemaining()) {
            b = in.get();

            switch (b) {
            case 0x06:
                size = in.position() - position - 1;

                if (size > 0) {
                    in.position(position);
                    String str = in.getString(size, decoder);

                    value = str.split(";");
                    in.skip(1);
                } else {
                    value = null;
                }

                statisInfos.put(index++, value);

                position = in.position();
                break;
            default:

            }
        }

        queue.popLimit(in);
        responseMessage.setStatisticInfos(statisInfos);

        return responseMessage;
    }

    private void decodeMessageDocs(CacheResponseMessage responseMessage, IoSession session, IoBuffer in,
            CharsetDecoder decoder, int length, LimitQueue queue) throws Exception {

        queue.pushLimit(in, length);

        List<CacheResponseMessageDoc> docs = new ArrayList<CacheResponseMessageDoc>();
        CacheResponseMessageDoc document = null;

        while (in.remaining() > 23) {
            CacheResponseMessageDoc doc = factory.createResponseMessageDoc();

            document = DocumentDecoder.decodeResponseMessageDoc(in, doc, decoder);

            if (document != null) {
                docs.add(document);
            }
        }

        responseMessage.setDocs(docs);
        queue.popLimit(in);
    }
}
