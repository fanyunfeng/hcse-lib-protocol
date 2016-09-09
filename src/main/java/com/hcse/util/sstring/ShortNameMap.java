package com.hcse.util.sstring;

import java.util.HashMap;
import java.util.Map;

public class ShortNameMap {
    public Map<String, String> shortNameMap;

    private static class Holder {
        static ShortNameMap instance = new ShortNameMap();
    }

    public static Map<String, String> getInstance() {
        return Holder.instance.shortNameMap;
    }

    private ShortNameMap() {
        shortNameMap = new HashMap<String, String>();

        shortNameMap.put("sr", "searchString");
        shortNameMap.put("be", "begin");
        shortNameMap.put("en", "end");
        shortNameMap.put("so", "sort");
        shortNameMap.put("st", "statistic");
        shortNameMap.put("vi", "vip");
    }
}
