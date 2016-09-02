package com.hcse.protocol.cache.message;

public class CacheResponseHeader {

    private String messageFlag;// 消息标记

    private int totalCount;// 检索总个数,缩近前---线路的总条数

    private int realCount;// 本次返回个数,后----线路折叠后的总条数

    private int mmtCount;// MMT会员个数，还用当返回结果为空的时候用来保存错误码 -----物流商数量

    private int resultNum;// 本次返回多少个结果

    private int returnLength;// 检索结果buf的 长度

    private int statisLength;// 统计结果buf的长度

    private int tipLength;// 提示词长度

    private int saurusLength;// 同义词长度

    private int parameterLength;// 参数

    private int topKeywordLength;// 匹配关键词

    private int classifyInfoLength;// 分类

    private int cutWordLength;// 搜索词切词结果

    private int contentLength;// 后续包的长度

    private int headerLength;

    public String getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(String messageFlag) {
        this.messageFlag = messageFlag;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getRealCount() {
        return realCount;
    }

    public void setRealCount(int realCount) {
        this.realCount = realCount;
    }

    public int getMmtCount() {
        return mmtCount;
    }

    public void setMmtCount(int mmtCount) {
        this.mmtCount = mmtCount;
    }

    public int getResultNum() {
        return resultNum;
    }

    public void setResultNum(int resultNum) {
        this.resultNum = resultNum;
    }

    public int getReturnLength() {
        return returnLength;
    }

    public int setReturnLength(int returnLen) {
        return this.returnLength = returnLen;
    }

    public int getStatisticLength() {
        return statisLength;
    }

    public int setStatisLength(int statisLen) {
        return this.statisLength = statisLen;
    }

    public int getTipLength() {
        return tipLength;
    }

    public int setTipLength(int tipLen) {
        return this.tipLength = tipLen;
    }

    public int getSaurusLength() {
        return saurusLength;
    }

    public int setSaurusLength(int saurusLen) {
        return this.saurusLength = saurusLen;
    }

    public int getParameterLength() {
        return parameterLength;
    }

    public int setParameterLength(int parameterLen) {
        return this.parameterLength = parameterLen;
    }

    public int getTopKeywordLength() {
        return topKeywordLength;
    }

    public int setTopkeywordLength(int topkeywordLen) {
        return this.topKeywordLength = topkeywordLen;
    }

    public int getClassifyInfoLength() {
        return classifyInfoLength;
    }

    public int setClassifyInfoLength(int classifyinfoLen) {
        return this.classifyInfoLength = classifyinfoLen;
    }

    public int getCutWordLength() {
        return cutWordLength;
    }

    public int setCutWordLength(int cutwordLen) {
        return this.cutWordLength = cutwordLen;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLen) {
        this.contentLength = contentLen;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    @Override
    public String toString() {
        return "SearchResponesHeader [messageFlag=" + messageFlag + ", totalCount=" + totalCount + ", realCount="
                + realCount + ", mmtCount=" + mmtCount + ", resultNum=" + resultNum + ", returnLength=" + returnLength
                + ", statisLength=" + statisLength + ", tipLength=" + tipLength + ", saurusLength=" + saurusLength
                + ", parameterLength=" + parameterLength + ", topKeywordLength=" + topKeywordLength
                + ", classifyInfoLength=" + classifyInfoLength + ", cutWordLength=" + cutWordLength
                + ", contentLength=" + contentLength + ", headerLength=" + headerLength + "]";
    }

}
