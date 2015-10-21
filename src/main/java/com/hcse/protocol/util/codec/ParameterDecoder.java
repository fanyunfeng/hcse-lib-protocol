package com.hcse.protocol.util.codec;

import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.hcse.cache.protocol.message.CacheResponseMessage;
import com.hcse.protocol.util.LimitQueue;
import com.hcse.protocol.util.packet.Parameter;

public class ParameterDecoder {
    private static final byte PARAMETER_SPLIT_FLAG = 0x05;
    private static final byte PARAMETER_NAME_SPLIT_FLAG = 0x06;
    private static final byte PARAMETER_VALUE_SPLIT_FLAG = 0x07;
    private static final byte PARAMETER_D2KEY_SPLIT_FLAG = 0x08;

    public static List<Parameter> decodeParameter(CacheResponseMessage responseMessage, IoSession session, IoBuffer in,
            CharsetDecoder decoder, int length, LimitQueue queue) throws Exception {

        queue.pushLimit(in, length);

        List<Parameter> list = new ArrayList<Parameter>();

        String value = null;
        String d2Key = null;
        Parameter current = new Parameter();

        byte b;
        int position = in.position();
        int size = 0;

        while (in.hasRemaining()) {
            b = in.get();

            switch (b) {
            case PARAMETER_SPLIT_FLAG: {
                if (current.getName() != null) {
                    list.add(current);

                    position = in.position();
                    current = new Parameter();
                }
                break;
            }
            case PARAMETER_NAME_SPLIT_FLAG: {
                size = in.position() - position;
                --size;

                if (size > 0) {
                    in.position(position);
                    current.setName(in.getString(size, decoder));

                    in.skip(1);
                }
                position = in.position();
                break;
            }
            case PARAMETER_VALUE_SPLIT_FLAG: {
                if (value != null) {
                    size = in.position() - position;
                    --size;

                    if (size > 0) {
                        in.position(position);
                        d2Key = in.getString(size, decoder);

                        in.skip(1);
                    } else {
                        d2Key = "";
                    }

                    // save value
                    current.addParameter(value, d2Key);

                    // reset
                    value = null;
                    d2Key = null;
                }
                position = in.position();
                break;
            }
            case PARAMETER_D2KEY_SPLIT_FLAG: {
                size = in.position() - position;
                --size;

                if (size > 0) {
                    in.position(position);
                    value = in.getString(size, decoder);

                    in.skip(1);
                } else {
                    value = "";
                }

                position = in.position();
                break;
            }
            default:
                break;
            }
        }

        if (current.getName() != null) {
            list.add(current);
        }

        queue.popLimit(in);
        return list;
    }
}
