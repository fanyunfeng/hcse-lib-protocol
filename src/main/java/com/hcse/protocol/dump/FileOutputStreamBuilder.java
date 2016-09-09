package com.hcse.protocol.dump;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.hcse.util.Md5Lite;

public class FileOutputStreamBuilder implements OutputStreamBuilder {
    protected String dir;
    protected String suffix;

    public FileOutputStreamBuilder(String dir, String suffix) {
        this.dir = dir;
        this.suffix = suffix;
    }

    @Override
    public OutputStream creatOutputStream(long tag) {
        String filename = Md5Lite.toString(tag);

        filename = dir + File.separator + filename + "." + suffix;

        return creatOutputStream(filename);
    }

    public OutputStream creatOutputStream(String fileName) {
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
            if (os != null) {
                os.close();
            }

        } catch (IOException e) {

        }
    }
}
