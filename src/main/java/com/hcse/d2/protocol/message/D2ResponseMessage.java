package com.hcse.d2.protocol.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class D2ResponseMessage {
    protected static final Logger logger = Logger.getLogger(D2ResponseMessage.class);

    private int headerLength;
    private int bodyLength;
    private int docsLength;
    private int classLength;

    private int docsCount;

    private int totalCount;
    private int realCount;
    private int mmtCount;

    private int machineId;
    private int indent;

    private ArrayList<D2ResponseMessageDoc> docs;

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

    public void addContentLength(int len) {
        bodyLength += len;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public int getDocsCount() {
        return docsCount;
    }

    public void setDocsCount(int count) {
        if (this.docsCount != count) {
            docs = new ArrayList<D2ResponseMessageDoc>(count);

            for (int i = 0; i < count; i++) {
                docs.add(new D2ResponseMessageDoc());
            }
        }

        this.docsCount = count;
    }

    public List<D2ResponseMessageDoc> getDocs() {
        return docs;
    }

    public D2ResponseMessageDoc getDocById(int id) {
        return docs.get(id);
    }

    public int getDocsLength() {
        return docsLength;
    }

    public void setDocsLength(int docsLength) {
        this.docsLength = docsLength;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public int getClassLength() {
        return classLength;
    }

    public void setClassLength(int classLength) {
        this.classLength = classLength;
    }

    public void dataProcess() {

    }

    public void dump() {
        logger.info("machineId:" + machineId);
        logger.info("docsLength:" + docsLength);
        logger.info("classLength:" + classLength);
        logger.info("docsCount:" + docsCount);

        int i = 0;
        for (D2ResponseMessageDoc doc : docs) {
            logger.info(String.format("%d\t md5Lite:%X weight:%d indentValue:%d indentCount:%d indentPage:%d", i,
                    doc.getMd5Lite(), doc.getWeight(), doc.getIndentValue(), doc.getIndentCount(), doc.getIndentPage()));

            i++;
        }

    }
}
