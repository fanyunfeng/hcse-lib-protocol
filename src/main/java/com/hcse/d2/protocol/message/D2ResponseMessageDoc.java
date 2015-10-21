package com.hcse.d2.protocol.message;

public class D2ResponseMessageDoc {
    private long md5Lite;
    private long weight;

    private int indentValue;
    private int indentCount;
    private int indentPage;

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

    public void dataProcess() {

    }
}
