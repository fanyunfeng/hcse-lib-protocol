package com.hcse.protocol.d6.message;

import com.hcse.protocol.util.Decoder;

public class D6RequestMessageDoc {
    private long md5Lite;
    private long weight;

    private byte machineId;
    private int indentCount;

    public long getMd5Lite() {
        return md5Lite;
    }

    public void setMd5Lite(String id) {
        md5Lite = Decoder.String2MD5Lite(id);
    }

    public void setMd5Lite(long md5Lite) {
        this.md5Lite = md5Lite;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public byte getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = (byte) machineId;
    }

    public int getIndentCount() {
        return indentCount;
    }

    public void setIndentCount(int indentCount) {
        this.indentCount = indentCount;
    }
}
