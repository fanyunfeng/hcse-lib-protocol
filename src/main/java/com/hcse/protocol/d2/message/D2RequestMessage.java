package com.hcse.protocol.d2.message;

import com.hcse.service.BaseRequest;

public class D2RequestMessage implements BaseRequest {
    private String searchString;

    private boolean indent = false;
    private int begin = 1;
    private int end = 15;

    private boolean vip;
    private int sort = 1;

    final String comIp = "7f00000100000000";
    private String userIp;
    private int statisic;

    private String serviceAddress;

    public D2RequestMessage() {
        this.userIp = "127.0.0.1";
    }

    public D2RequestMessage(String ip, int begin, int end, int statistic, String searchString) {
        this.userIp = ip;
        this.begin = begin;
        this.end = end;
        this.statisic = statistic;
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

    public int getStatisic() {
        return statisic;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setStatisic(int statisic) {
        this.statisic = statisic;
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

    public int getBegin() {
        return begin;
    }

    public void setBegin(int beginItem) {
        this.begin = beginItem;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int endItem) {
        this.end = endItem;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
