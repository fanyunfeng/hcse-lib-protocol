package com.hcse.protocol.d2.message;

import com.hcse.protocol.BaseResponseDoc;

public class D2ResponseMessageDoc implements BaseResponseDoc {
    private long md5Lite;
    private long weight;

    private int indentValue;
    private int indentCount;
    private int indentPage;

    private int po;
    private int ph;

    public long getMd5Lite() {
        return md5Lite;
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

    public int getIndentValue() {
        return indentValue;
    }

    public void setIndentValue(int indentValue) {
        this.indentValue = indentValue;
    }

    public int getIndentCount() {
        return indentCount;
    }

    public void setIndentCount(int indentCount) {
        this.indentCount = indentCount;
    }

    public int getIndentPage() {
        return indentPage;
    }

    public void setIndentPage(int indentPage) {
        this.indentPage = indentPage;
    }

    public int getPo() {
        return po;
    }

    public void setPo(int po) {
        this.po = po;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

    public void dataProcess() {

    }
}
