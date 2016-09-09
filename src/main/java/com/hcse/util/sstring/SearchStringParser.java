package com.hcse.util.sstring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.hcse.protocol.BaseRequest;
import com.hcse.util.Md5Lite;
import com.hcse.util.mkv.MKVParser;
import com.hcse.util.mkv.MKVParserHandler;

public class SearchStringParser {
    public static Map<String, String> parse(String str, final Map<String, String> nameMap) {
        final HashMap<String, String> o = new HashMap<String, String>();

        MKVParser parser = new MKVParser();

        if (parser.parse(str, new MKVParserHandler() {

            @Override
            public void onKvPaire(String k, String v) {
                String name = nameMap.get(k);

                if (name == null) {
                    o.put(k, v);
                } else {
                    o.put(name, v);
                }
            }

        })) {
            return o;
        }

        return null;
    }

    public static BaseRequest buildMessage(BaseRequest o, Map<String, String> map) {
        try {
            BeanUtils.populate(o, map);

            return o;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public static List<? extends BaseRequest> loadRequest(String fileName, Map<String, String> shortKeyMap,
            RequestFactory<BaseRequest> factory) {
        
        List<BaseRequest> requests = new ArrayList<BaseRequest>();

        FileInputStream in = null;

        try {
            in = new FileInputStream(fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            String line = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                Map<String, String> ret = SearchStringParser.parse(line, shortKeyMap);

                BaseRequest req = SearchStringParser.buildMessage(factory.create(), ret);

                if (req != null) {
                    long tag = Md5Lite.digestLong(line);
                    
                    req.setTag(tag);
                    requests.add(req);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        return requests;
    }

    public static List<Object> parseRequest(String line, Map<String, String> shortKeyMap, RequestFactory factory) {
        List<Object> requests = new ArrayList<Object>();

        Map<String, String> ret = SearchStringParser.parse(line, shortKeyMap);

        Object req = SearchStringParser.buildMessage(factory.create(), ret);

        requests.add(req);

        return requests;
    }
}
