package com.hcse.protocol.util.packet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hcse.protocol.BasePacket;
import com.hcse.protocol.BaseResponseDoc;
import com.hcse.protocol.util.Decoder;

public class BaseDoc extends BasePacket implements BaseResponseDoc {
    protected static final Logger logger = Logger.getLogger(BaseDoc.class);

    private long md5Lite;
    private long weight;

    private int groupCount;
    private String md5LiteString;

    private FieldsMap prototype;
    private ArrayList<String> values;

    public BaseDoc() {
        this.prototype = FieldsMap.create(0);
        values = new ArrayList<String>(prototype.size());
    }

    public BaseDoc(FieldsMap prototype) {
        this.prototype = prototype;
        values = new ArrayList<String>(prototype.size());
    }

    public void setPrototype(FieldsMap prototype) {
        this.prototype = prototype;
    }

    public long getMd5Lite() {
        return md5Lite;
    }

    public String getMd5LiteString() {
        if (md5LiteString == null) {
            md5LiteString = Long.toHexString(md5Lite).toLowerCase();
        }

        return md5LiteString;
    }

    public void setMd5Lite(long md5Lit) {
        this.md5Lite = md5Lit;
    }

    public void setMd5LiteString(String md5Lite) {
        this.md5LiteString = md5Lite.toUpperCase();
        this.md5Lite = Decoder.String2MD5Lite(md5Lite);
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupSize) {
        this.groupCount = groupSize;
    }

    public void setFieldValue(String name, String value) {
        int index = prototype.getIndex(name);

        if (index < 0) {
            logger.error("unknown field:" + name + " value:" + value);
            return;
        }

        if (index < values.size()) {
            values.set(index, value);
        } else {
            for (int i = values.size(); i < index; ++i) {
                values.add("");
            }

            values.add(value);
        }
    }

    public List<String> getValues() {
        return values;
    }

    public List<String> getNames() {
        return prototype.getFields();
    }

    public void setFieldValue(int index, String value) {
        values.add(index, value);
    }

    public void setValues(List<String> v) {
        values.addAll(v);
    }

    public void dump() {
        logger.info("!---------------- document ----------------");
        int index = 0;
        logger.info(String.format("md5Lite:%X weight:%d count:%d", md5Lite, weight, groupCount));

        for (String v : values) {
            if (index >= prototype.size()) {
                logger.info(String.format("XXXX:%s", v));
            } else {
                logger.info(String.format("%s:%s", prototype.getFieldName(index), v));
            }

            index++;
        }
    }
}
