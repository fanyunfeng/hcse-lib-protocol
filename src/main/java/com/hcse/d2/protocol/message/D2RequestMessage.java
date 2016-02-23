package com.hcse.d2.protocol.message;

import com.hcse.service.BaseRequest;

public class D2RequestMessage implements BaseRequest {
    private String searchString;

    private boolean indent;
    private int beginItem;
    private int endItem;

    private boolean vip;
    private int sortId;

    final String comIp = "7f00000100000000";
    private String userIp;
    private int statisicId;

    private String serviceAddress;

    public D2RequestMessage(String ip, int begin, int end, int statistic, String searchString) {
        this.userIp = ip;
        this.beginItem = begin;
        this.endItem = end;
        this.statisicId = statistic;
        this.searchString = searchString;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getComIp() {
        return comIp;
    }

    public int getStatisicId() {
        return statisicId;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setStatisicId(int statisicId) {
        this.statisicId = statisicId;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public boolean isIndent() {
        return indent;
    }

    public void setIndent(boolean indent) {
        this.indent = indent;
    }

    public int getBeginItem() {
        return beginItem;
    }

    public void setBeginItem(int beginItem) {
        this.beginItem = beginItem;
    }

    public int getEndItem() {
        return endItem;
    }

    public void setEndItem(int endItem) {
        this.endItem = endItem;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getSortMethod() {
        return sortId;
    }

    public void setSortMethod(int sortId) {
        this.sortId = sortId;
    }

}
