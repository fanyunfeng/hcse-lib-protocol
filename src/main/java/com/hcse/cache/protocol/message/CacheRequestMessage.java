package com.hcse.cache.protocol.message;

import com.hcse.service.BaseRequest;

public class CacheRequestMessage implements BaseRequest {

    // 协议的标识 3b07^2k6 和 3b06^2k6 -----> 默认为 3b06^2k6

    // 写死可以不变
    private String comIp = "7f00000100000000";

    // 从客户端穿来的用户IP 比如 192.168.245.1
    private String userIp;

    // 开始条数
    private long begingItem;

    // 结束条数
    private long endItem;

    // 排序方式
    private long sortMethod = 1;

    // 统计方式
    private long statisic;

    // 是否缩进 --默认情况下为1，当选择相似路径打开的时候 为0
    private int indent = 1;

    // 是否刷新cache
    private int freshFlag = 0;

    // 是否站内检索
    private int searchSign = 1;

    // 是否显示VIP
    private int showVip = 1;

    // 统计参数的终极分类 ---- >目前无用，传空串即可
    private String assortNum;

    // 统计参数
    private String sc = "0";

    // 命中的行业编号------》目前无用，传空串即可
    private String tradeNum;

    private long searchFlag = 3l;

    // 具体检索串
    private String searchString;

    private String serviceAddress;

    public CacheRequestMessage() {
    }

    public CacheRequestMessage(String userIp, long beginItem, long endItem, long statis, String searchString) {
        this.userIp = userIp;
        this.begingItem = beginItem;
        this.endItem = endItem;
        this.statisic = statis;
        this.searchString = searchString;
    }

    public CacheRequestMessage(String userIp, long beginItem, long endItem, String searchString) {
        this.userIp = userIp;
        this.begingItem = beginItem;
        this.endItem = endItem;
        this.searchString = searchString;
    }

    public String getComIp() {
        return comIp;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public long getBeginItem() {
        return begingItem;
    }

    public void setBeginItem(long begNum) {
        this.begingItem = begNum;
    }

    public long getEndItem() {
        return endItem;
    }

    public void setEndItem(long endNum) {
        this.endItem = endNum;
    }

    public long getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(long sortMethod) {
        this.sortMethod = sortMethod;
    }

    public long getStatisic() {
        return statisic;
    }

    public void setStatisic(long statis) {
        this.statisic = statis;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public int getFreshFlag() {
        return freshFlag;
    }

    public void setFreshFlag(int freshFlag) {
        this.freshFlag = freshFlag;
    }

    public int getSearchSign() {
        return searchSign;
    }

    public void setSearchSign(int searchSign) {
        this.searchSign = searchSign;
    }

    public int getShowVip() {
        return showVip;
    }

    public void setShowVip(int showVip) {
        this.showVip = showVip;
    }

    public String getAssortNum() {
        return assortNum;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public long getSearchFlag() {
        return searchFlag;
    }

    public void setSearchFlag(long searchFlag) {
        this.searchFlag = searchFlag;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchStr) {
        this.searchString = searchStr;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String cacheAddress) {
        this.serviceAddress = cacheAddress;
    }

    public void dataProcess() {

    }

    @Override
    public String toString() {
        return "CacheRequestMessage [comIp=" + comIp + ", userIp=" + userIp + ", begingItem=" + begingItem
                + ", endItem=" + endItem + ", sortMethod=" + sortMethod + ", statisic=" + statisic + ", indent="
                + indent + ", freshFlag=" + freshFlag + ", searchSign=" + searchSign + ", showVip=" + showVip
                + ", assortNum=" + assortNum + ", sc=" + sc + ", tradeNum=" + tradeNum + ", searchFlag=" + searchFlag
                + ", searchString=" + searchString + ", serviceAddress=" + serviceAddress + "]";
    }
}
