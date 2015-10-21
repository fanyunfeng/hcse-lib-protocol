package com.hcse.d6.protocol.message;

import java.util.ArrayList;

public class D6RequestMessage {
    private String searchString;

    private int docsCount;
    private ArrayList<D6RequestMessageDoc> docs;

    private String serviceAddress;

    public D6RequestMessage(String searchString) {
        this.searchString = searchString;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public int getDocsCount() {
        return docsCount;
    }

    public void setDocsCount(int docsCount) {
        if (this.docsCount != docsCount) {
            if (docs == null) {
                docs = new ArrayList<D6RequestMessageDoc>();
            }

            int count = docsCount - docs.size();

            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    docs.add(new D6RequestMessageDoc());
                }
            } else {
                while (count < 0) {
                    count++;
                    docs.remove(docs.size() - 1);
                }
            }
        }

        this.docsCount = docsCount;
    }

    public ArrayList<D6RequestMessageDoc> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<D6RequestMessageDoc> docs) {
        this.docs = docs;
    }

    public D6RequestMessageDoc getDocById(int id) {
        return docs.get(id);
    }
}
