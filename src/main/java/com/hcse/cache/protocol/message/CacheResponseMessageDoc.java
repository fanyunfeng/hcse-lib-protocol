package com.hcse.cache.protocol.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hcse.protocol.util.packet.BasePacket;
import com.hcse.protocol.util.packet.FieldsMap;

public class CacheResponseMessageDoc extends BasePacket {
    protected static final Logger logger = Logger.getLogger(CacheResponseMessageDoc.class);

    private long md5Lite;
    private long weight;

    private int groupCount;

    private FieldsMap prototype;
    private ArrayList<String> values;

    public CacheResponseMessageDoc() {
        this.prototype = FieldsMap.create(0);
        values = new ArrayList<String>(prototype.size());
    }

    public CacheResponseMessageDoc(FieldsMap prototype) {
        this.prototype = prototype;
        values = new ArrayList<String>(prototype.size());
    }

    public long getMd5Lite() {
        return md5Lite;
    }

    public void setMd5Lite(long md5Lit) {
        this.md5Lite = md5Lit;
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

    public void setFieldValue(int index, String value) {
        if (index < prototype.size()) {
            values.add(index, value);
        }
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
