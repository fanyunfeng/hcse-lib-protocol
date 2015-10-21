package com.hcse.d6.protocol.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class D6ResponseMessage {
    protected static final Logger logger = Logger.getLogger(D6ResponseMessage.class);

    private int headerLength;
    private int docsLength;
    private int docsCount;

    private ArrayList<D6ResponseMessageDoc> docs = new ArrayList<D6ResponseMessageDoc>();

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public int getDocsCount() {
        return docsCount;
    }

    public void setDocsCount(int count) {
        this.docsCount = count;
    }

    public List<D6ResponseMessageDoc> getDocs() {
        return docs;
    }

    public D6ResponseMessageDoc getDocById(int id) {
        return docs.get(id);
    }

    public int getDocsLength() {
        return docsLength;
    }

    public void setDocsLength(int docsLength) {
        this.docsLength = docsLength;
    }

    public long getBodyLength() {
        return docsLength;
    }

    public void appendDoc(D6ResponseMessageClientDoc doc) {
        docs.add(doc);
    }

    public void dataProcess() {

    }

    public void dump() {
        logger.info("docsLength:" + docsLength);
        logger.info("docsCount:" + docsCount);

        int i = 0;
        for (D6ResponseMessageDoc doc : docs) {
            doc.dump(i);
            i++;
        }
    }
}
