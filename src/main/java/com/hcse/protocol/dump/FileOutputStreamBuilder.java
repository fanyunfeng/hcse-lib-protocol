package com.hcse.protocol.dump;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileOutputStreamBuilder implements OutputStreamBuilder {
    private String fileName;

    public void setFileName(String name) {
        fileName = name;
    }

    @Override
    public OutputStream creatOutputStream() {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {

        }

        return os;
    }

    @Override
    public void destory(OutputStream os) {
        try {
            os.close();
        } catch (IOException e) {

        }
    }
}
