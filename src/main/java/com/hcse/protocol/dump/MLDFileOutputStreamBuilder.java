package com.hcse.protocol.dump;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import com.hcse.util.Md5Lite;

public class MLDFileOutputStreamBuilder extends FileOutputStreamBuilder {
    @Override
    public OutputStream creatOutputStream(long tag) {
        String name = Md5Lite.toString(tag);
        String path = dir + File.separator + MLDPathName(name, levelLen);

        File file = new File(path);

        file.mkdirs();

        String filename = path + File.separator + name + "." + suffix;

        return super.creatOutputStream(filename);
    }

    public static String MLDFileName(String dir, String tag, List<Integer> levelLen, String suffix) {
        return dir + MLDFileName(tag, levelLen);
    }

    public static String MLDFileName(String tag, List<Integer> levelLen) {
        int start = 0;
        int end = 0;

        StringBuilder sb = new StringBuilder();

        for (int len : levelLen) {
            end = start + len;
            sb.append(tag.substring(start, end));

            sb.append(File.separator);

            start = end;
        }

        sb.append(tag);

        return sb.toString();
    }

    public static String MLDPathName(long tag, int[] levelLen) {
        int start = 0;
        int end = 0;

        String name = Md5Lite.toString(tag);
        StringBuilder sb = new StringBuilder();

        for (int len : levelLen) {
            end = start + len;
            sb.append(name.substring(start, end));

            sb.append(File.separator);

            start = end;
        }

        return sb.toString();
    }

    public static String MLDPathName(String tag, int[] levelLen) {
        int start = 0;
        int end = 0;

        StringBuilder sb = new StringBuilder();

        for (int len : levelLen) {
            end = start + len;
            sb.append(tag.substring(start, end));

            sb.append(File.separator);

            start = end;
        }

        return sb.toString();
    }

    private int[] levelLen;

    public MLDFileOutputStreamBuilder(String dir, int[] levelLen, String suffix) {
        super(dir, suffix);

        if (levelLen.length >= 2 && levelLen[levelLen.length - 1] == -1) {
            int sum = 0;
            for (int x = 0; x < levelLen.length - 1; x++) {
                sum += levelLen[x];
            }

            if (sum >= 16) {
                return;
            } else {
                levelLen[levelLen.length - 1] = 16 - sum;
            }
        }

        this.levelLen = levelLen;
    }
}
