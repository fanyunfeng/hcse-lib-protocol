package com.hcse.protocol.dump;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.hcse.protocol.BaseResponse;
import com.hcse.protocol.d2.message.D2ResponseMessage;
import com.hcse.protocol.d2.message.D2ResponseMessageDoc;

public class D2ResponseDump implements ResponseDump {
    protected final Logger logger = Logger.getLogger(D2ResponseDump.class);

    private boolean pretty = true;
    private String charset = "utf8";
    private ObjectMapper objectMapper = new ObjectMapper();

    private OutputStreamBuilder outputStreamBuilder;

    D2ResponseDump(OutputStreamBuilder osb) {
        this.outputStreamBuilder = osb;
    }

    public void dump(BaseResponse res) {
        OutputStream os = outputStreamBuilder.creatOutputStream();

        D2ResponseMessage d2Response = (D2ResponseMessage) res;

        try {
            OutputStreamWriter writer;

            writer = new OutputStreamWriter(os, charset);

            JsonGenerator generator = objectMapper.getJsonFactory().createJsonGenerator(writer);

            if (pretty) {
                MyPrettyPrinter pp = new MyPrettyPrinter();
                generator.setPrettyPrinter(pp);
            }

            //
            generator.writeStartArray();
            {
                List<D2ResponseMessageDoc> list = d2Response.getDocs();

                for (D2ResponseMessageDoc doc : list) {
                    generator.writeStartObject();
                    {
                        generator.writeStringField("md5Lite", Long.toHexString(doc.getMd5Lite()).toUpperCase());
                        generator.writeNumberField("weight", doc.getWeight());

                        generator.writeNumberField("indentValue", doc.getIndentValue());
                        generator.writeNumberField("getIndentCount", doc.getIndentCount());
                        generator.writeNumberField("getIndentPage", doc.getIndentPage());

                        generator.writeNumberField("po", doc.getPo());
                        generator.writeNumberField("ph", doc.getPh());

                    }
                    generator.writeEndObject();
                }
            }
            generator.writeEndArray();

            //
            generator.flush();
            writer.flush();

        } catch (UnsupportedEncodingException e) {
            logger.error("dump response failed.", e);
        } catch (IOException e) {
            logger.error("dump response failed.", e);
        } finally {
            outputStreamBuilder.destory(os);
        }
    }
}
