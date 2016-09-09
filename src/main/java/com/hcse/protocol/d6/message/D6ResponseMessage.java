package com.hcse.protocol.d6.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.hcse.protocol.BaseResponse;
import com.hcse.protocol.BasePacket;

public class D6ResponseMessage {
    protected static final Logger logger = Logger.getLogger(D6ResponseMessage.class);

    private int headerLength;
    private int docsLength;
    private int docsCount;

    private IoBuffer buffer;
    private ArrayList<BasePacket> docs = new ArrayList<BasePacket>();

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

    public List<BasePacket> getDocs() {
        return docs;
    }

    public BasePacket getDocById(int id) {
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

    public void setBuffer(IoBuffer buffer) {
        this.buffer = buffer;
    }

    public IoBuffer getBuffer() {
        return buffer;
    }

    public void dump() {
        logger.info("docsLength:" + docsLength);
        logger.info("docsCount:" + docsCount);

        int i = 0;
        for (BasePacket doc : docs) {
            doc.dump(i);
            i++;
        }
    }
}
