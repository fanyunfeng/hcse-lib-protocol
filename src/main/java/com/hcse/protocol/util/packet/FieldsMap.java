package com.hcse.protocol.util.packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FieldsMap implements Cloneable {
    private ArrayList<String> fields;
    private HashMap<String, Integer> fieldsMap;

    static FieldsMap[] masp = new FieldsMap[2];

    public static FieldsMap create(int type) {
        if (masp[type] == null) {
            masp[type] = new FieldsMap(type);
        }

        return masp[type];
    }

    private FieldsMap(int type) {
        switch (type) {
        case 0:
            createField4Common();
            break;
        case 1:
            createFields4Logistics();
            break;
        default:
        }
    }

    public void createFields4Logistics() {
        fields = new ArrayList<String>();
        fieldsMap = new HashMap<String, Integer>();

        addField("RQ");
        addField("CQ");
        addField("PC");
        addField("ZS");
        addField("AS");
        addField("BZ");
        addField("JY");
        addField("MI");
        addField("TI");
        addField("TP");
        addField("TT");
        addField("IM");
        addField("CM");
        addField("CY");
        addField("CZ");
        addField("CC");
        addField("CV");
        addField("VE");
        addField("CO");
        addField("CB");
        addField("LA");
        addField("ID");
        addField("QY");
        addField("UN");
        addField("VN");
        addField("TM");
        addField("UP");
        addField("BA");
        addField("BB");
        addField("GN");
        addField("SN");
        addField("ZF");
        addField("BQ");
        addField("ET");
        addField("PQ");
        addField("GD");
        addField("SS");
        addField("TB");
        addField("ZY");
        addField("PA");
        addField("QU");
        addField("SL");
        addField("AG");
        addField("SU");
        addField("TY");
        addField("ME");
        addField("IB");
        addField("CT");
        addField("BD");
        addField("HD");
        addField("MO");
        addField("VU");
        addField("PT");
        addField("MM");
        addField("UL");
        addField("PM");
        addField("QQ");
        addField("BU");
        addField("PR");
        addField("TA");
        addField("TC");
        addField("YP");
        addField("XP");
    }

    public void createField4Common() {
        fields = new ArrayList<String>();
        fieldsMap = new HashMap<String, Integer>();

        addField("RQ");
        addField("CQ");
        addField("PC");
        addField("ZS");
        addField("AS");
        addField("BZ");
        addField("JY");
        addField("MI");
        addField("TI");
        addField("TP");
        addField("TT");
        addField("IM");
        addField("CM");
        addField("CY");
        addField("CZ");
        addField("CC");
        addField("CV");
        addField("VE");
        addField("CO");
        addField("CB");
        addField("LA");
        addField("ID");
        addField("QY");
        addField("UN");
        addField("VN");
        addField("TM");
        addField("UP");
        addField("BA");
        addField("BB");
        addField("GN");
        addField("SN");
        addField("ZF");
        addField("BQ");
        addField("ET");
        addField("PQ");
        addField("GD");
        addField("SS");
        addField("TB");
        addField("ZY");
        addField("PA");
        addField("QU");
        addField("SL");
        addField("AG");
        addField("SU");
        addField("TY");
        addField("ME");
        addField("IB");
        addField("CT");
        addField("BD");
        addField("HD");
        addField("MO");
        addField("VU");
        addField("PT");
        addField("MM");
        addField("UL");
        addField("PM");
        addField("QQ");
        addField("BU");
        addField("TV");
        addField("TU");
        addField("CA");
        addField("GY");
        addField("UD");
        addField("YP");
        addField("XP");
        addField("ML");
        addField("HE");
        addField("HB");
        addField("TE");
        addField("FX");
    }

    public void addField(String name) {
        fields.add(name);

        fieldsMap.put(name, fieldsMap.size());
    }

    public int getIndex(String name) {
        Integer index = fieldsMap.get(name);

        if (index == null) {
            return -1;
        }

        return index;
    }

    public String getFieldName(int index) {
        return fields.get(index);
    }

    public List<String> getFields() {
        return fields;
    }

    public int size() {
        return fields.size();
    }

    @SuppressWarnings("unchecked")
    public Object clone() {
        FieldsMap obj = new FieldsMap(-1);

        obj.fields = (ArrayList<String>) this.fields.clone();
        obj.fieldsMap = (HashMap<String, Integer>) this.fieldsMap.clone();

        return obj;
    }
}
