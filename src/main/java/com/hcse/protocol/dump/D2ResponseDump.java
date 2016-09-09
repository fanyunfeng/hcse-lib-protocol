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
import com.hcse.util.Md5Lite;

public class D2ResponseDump extends ResponseDump {
    protected final Logger logger = Logger.getLogger(D2ResponseDump.class);

    private boolean pretty = true;
    private String charset = "utf8";
    private ObjectMapper objectMapper = new ObjectMapper();

    public boolean isPretty() {
        return pretty;
    }

    public void setPretty(boolean pretty) {
        this.pretty = pretty;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void dump(OutputStream os, BaseResponse res) {
        D2ResponseMessage d2Response = (D2ResponseMessage) res;

        try {
            OutputStreamWriter writer;

            writer = new OutputStreamWriter(os, charset);

            JsonGenerator generator = objectMapper.getJsonFactory().createJsonGenerator(writer);

            if (pretty) {
                JsonPrettyPrinter pp = new JsonPrettyPrinter();
                generator.setPrettyPrinter(pp);
            }

            //
            generator.writeStartArray();
            {
                List<D2ResponseMessageDoc> list = d2Response.getDocs();

                for (D2ResponseMessageDoc doc : list) {
                    generator.writeStartObject();
                    {
                        generator.writeStringField("md5Lite", Md5Lite.toString(doc.getMd5Lite()));
                        generator.writeNumberField("weight", doc.getWeight());

                        generator.writeNumberField("indentValue", doc.getIndentValue());
                        generator.writeNumberField("indentCount", doc.getIndentCount());
                        generator.writeNumberField("indentPage", doc.getIndentPage());

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

        }
    }
}
