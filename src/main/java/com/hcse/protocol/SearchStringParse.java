package com.hcse.protocol;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.hcse.protocol.d2.message.D2RequestMessage;
import com.hcse.util.mkv.MKVParser;
import com.hcse.util.mkv.MKVParserHandler;

public class SearchStringParse {
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

    public static Object buildMessage(Object o, Map<String, String> map) {
        try {
            BeanUtils.populate(o, map);

            return o;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String str = "\"sr\": \"[S](([TX:TI:?????]))&([CL:CC:001])[F][DG:QU:3;5][PIC:1][K][?????][FC:1][REQ_ID:43bd8be5f7f26fba7ce313107b6d1ca0]\", \"be\": 1, \"en\": 25, \"so\": 1, \"st\": 0, \"in\": 0, \"vi\": 1, \"is\": 0";

        Map<String, String> shortKeyMap = new HashMap<String, String>();

        shortKeyMap.put("sr", "searchString");
        shortKeyMap.put("be", "begin");
        shortKeyMap.put("en", "end");
        shortKeyMap.put("so", "sort");
        shortKeyMap.put("st", "statistic");
        shortKeyMap.put("vi", "vip");

        Map<String, String> ret = SearchStringParse.parse(str, shortKeyMap);

        D2RequestMessage msg = new D2RequestMessage();

        buildMessage(msg, ret);

    }
}
