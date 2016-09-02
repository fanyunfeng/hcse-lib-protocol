package com.hcse.protocol.dump;

import java.io.File;
import java.util.List;

public class MLDFileOutputBuilder extends FileOutputStreamBuilder {
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

            sb.append(File.pathSeparator);

            start = end;
        }

        sb.append(tag);

        return sb.toString();
    }

    public static String MLDPathName(String tag, List<Integer> levelLen) {
        int start = 0;
        int end = 0;

        StringBuilder sb = new StringBuilder();

        for (int len : levelLen) {
            end = start + len;
            sb.append(tag.substring(start, end));

            sb.append(File.pathSeparator);

            start = end;
        }

        return sb.toString();
    }

    public MLDFileOutputBuilder(String dir, String tag, List<Integer> levelLen, String suffix) {

        String path = dir + MLDPathName(tag, levelLen);

        File file = new File(path);

        file.mkdirs();

        this.setFileName(file + File.pathSeparator + file + "." + suffix);
    }
}
