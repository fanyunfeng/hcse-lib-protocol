package com.hcse.d6.protocol.codec;

import java.io.OutputStreamWriter;
import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.hcse.d6.protocol.message.D6RequestMessage;
import com.hcse.d6.protocol.message.D6RequestMessageDoc;
import com.hcse.protocol.util.Constant;
import com.hcse.protocol.util.Encoder;

public class D6RequestMessageEncoder<T extends D6RequestMessage> implements MessageEncoder<T> {
    protected static final Logger logger = Logger.getLogger(D6RequestMessageEncoder.class);

    private static final String PACKAGE_MAGIC_V1 = "33334213";
    private static final String PACKAGE_MAGIC_V2 = "33334203";
    private static final String PACKAGE_MAGIC_V3 = "33336666";

    private static final int REQ_NORMAL_HEADER_LEN = 24;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {

        String magic = PACKAGE_MAGIC_V2;

        switch (message.getVersion()) {
        case 1:
            magic = PACKAGE_MAGIC_V1;
        case 2: {
            byte[] searchStrBuf = message.getSearchString().toUpperCase().getBytes(Constant.charsetName);

            int encodedSearchStringLength = searchStrBuf.length;

            int count = message.getDocsCount();
            int requestLength = (REQ_NORMAL_HEADER_LEN + encodedSearchStringLength + 21 * count);

            IoBuffer buf = IoBuffer.allocate(requestLength);

            buf.put(magic.getBytes());

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
            break;
        }
        case 3: {
            IoBuffer buf = IoBuffer.allocate(4096);

            OutputStreamWriter writer = new OutputStreamWriter(buf.asOutputStream(), Constant.charsetName);

            JsonGenerator generator = objectMapper.getJsonFactory().createJsonGenerator(writer);

            buf.skip(24);

            generator.writeStartObject();
            {
                generator.writeArrayFieldStart("RETURNDATA");
                {
                    generator.writeStartObject();
                    {
                        generator.writeNumberField("TYPE", 2);
                        generator.writeNumberField("NPOS", 5);
                        generator.writeNumberField("PAGENUM", 24);
                        generator.writeNumberField("lSchStrLen", 1);
                        generator.writeNumberField("ISNEEDTT", 1);

                        generator.writeArrayFieldStart("RESULTDATA");
                        {
                            for (D6RequestMessageDoc doc : message.getDocs()) {
                                generator.writeString(Long.toHexString(doc.getMd5Lite()).toUpperCase());
                            }
                        }
                        generator.writeEndArray();

                        generator.writeArrayFieldStart("RESULTCOMID");
                        {
                            for (D6RequestMessageDoc doc : message.getDocs()) {
                                generator.writeNumber(doc.getMachineId());
                            }
                        }
                        generator.writeEndArray();

                        generator.writeArrayFieldStart("MCID");
                        {
                            for (D6RequestMessageDoc doc : message.getDocs()) {
                                generator.writeNumber(doc.getMachineId());
                            }
                        }
                        generator.writeEndArray();

                        generator.writeArrayFieldStart("RESULTPOWER");
                        {
                            for (D6RequestMessageDoc doc : message.getDocs()) {
                                generator.writeNumber(doc.getWeight());
                            }
                        }
                        generator.writeEndArray();

                        generator.writeStringField("SEARCHSTRING", message.getSearchString());

                        generator.writeArrayFieldStart("RESULTINDENT");
                        {
                            for (D6RequestMessageDoc doc : message.getDocs()) {
                                generator.writeNumber(doc.getIndentCount());
                            }
                        }
                        generator.writeEndArray();

                    }
                    generator.writeEndObject();
                }
                generator.writeEndArray();
            }
            generator.writeEndObject();

            //
            generator.flush();
            writer.flush();

            // added header
            int position = buf.position();
            int size = position - 24;

            buf.position(0);

            //
            buf.put(PACKAGE_MAGIC_V3.getBytes());
            Encoder.encodeLongString(buf, 0, 8);
            Encoder.encodeLongString(buf, size, 8);

            // reset
            buf.position(position);

            //
            buf.flip();
            out.write(buf);

            break;
        }
        default: {
            return;
        }
        }
    }
}
