package com.hcse.protocol.cache.message;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.hcse.protocol.util.packet.BaseDoc;
import com.hcse.protocol.util.packet.Parameter;

public class CacheResponseMessage extends CacheResponseHeader {
    protected static final Logger logger = Logger.getLogger(CacheResponseMessage.class);

    private int parseState;

    private List<BaseDoc> docs;// 文档

    private String[] cutWords;

    private String tipWord;// 提示词

    private String saurusWord;// 同义词

    private String topKeyword;// 匹配关键词

    private String classCode;// 分类

    // 选中参数信息
    private List<Parameter> selectedParameters;

    private List<Parameter> unselectedParameters;

    private Map<Integer, String[]> statisInfos;

    public Map<Integer, String[]> getStatisInfos() {
        return statisInfos;
    }

    public void setStatisticInfos(Map<Integer, String[]> statisInfos) {
        this.statisInfos = statisInfos;
    }

    public List<Parameter> getSelectedParameters() {
        return selectedParameters;
    }

    public void setSelectedParameters(List<Parameter> selectedParamInfos) {
        this.selectedParameters = selectedParamInfos;
    }

    public List<Parameter> getUnselectedParameters() {
        return unselectedParameters;
    }

    public void setUnselectedParameters(List<Parameter> unSelectedParamInfos) {
        this.unselectedParameters = unSelectedParamInfos;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getTopKeyword() {
        return topKeyword;
    }

    public void setTopKeyword(String topKeyword) {
        this.topKeyword = topKeyword;
    }

    public String getSaurusWord() {
        return saurusWord;
    }

    public void setSaurusWord(String saurusWord) {
        this.saurusWord = saurusWord;
    }

    public String[] getCutWords() {
        return cutWords;
    }

    public void setCutWords(String[] cutWords) {
        this.cutWords = cutWords;
    }

    public String getTipWord() {
        return tipWord;
    }

    public void setTipWord(String tipWord) {
        this.tipWord = tipWord;
    }

    public List<BaseDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<BaseDoc> docs) {
        this.docs = docs;
    }

    public int getParseState() {
        return parseState;
    }

    public void setParseState(int parseState) {
        this.parseState = parseState;
    }

    public boolean dataProcess() {
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (statisInfos != null && statisInfos.size() > 0) {
            Set<Integer> set = statisInfos.keySet();
            for (int i : set) {
                sb.append(i).append("=").append(Arrays.toString(statisInfos.get(i))).append(",");
            }
        }

        return super.toString() + " --ResponseContentMessage [docInfos= " + docs + ",cutWords="
                + Arrays.toString(cutWords) + ", tipWord=" + tipWord + ", saurusWord=" + saurusWord + ", topKeyword="
                + topKeyword + ", classCode=" + classCode + ", selectedParamInfos=" + selectedParameters
                + ", unSelectedParamInfos=" + unselectedParameters + ", statisInfos= " + sb.toString() + "]";

    }

    public void dump() {
        logger.info("!---------------- document ----------------");

        if (docs == null) {
            return;
        }

        logger.info("document size:" + docs.size());

        for (BaseDoc doc : docs) {
            doc.dump();
        }
    }
}
