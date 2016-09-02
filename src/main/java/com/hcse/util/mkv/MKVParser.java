package com.hcse.util.mkv;

public class MKVParser {
    final int STATE_KSTART = 0;
    final int STATE_KEY = 1;
    final int STATE_QKEY = 2;

    final int STATE_SEP = 3;

    final int STATE_VSTART = 4;
    final int STATE_VALUE = 5;
    final int STATE_QVALUE = 6;

    private int state = STATE_KSTART;

    private String quote = "'\"";

    private String fieldSeperator = ",; ";

    private String kvSeperator = ":=";

    private String emptyChar = " \t";

    public MKVParser() {

    }

    private boolean isEmptyChar(char c) {
        return emptyChar.indexOf(c) != -1;
    }

    private boolean isFieldSeperator(char c) {
        return fieldSeperator.indexOf(c) != -1;
    }

    private boolean isKvSeperator(char c) {
        return kvSeperator.indexOf(c) != -1;
    }

    private boolean isQuote(char c) {
        return quote.indexOf(c) != -1;
    }

    public boolean parse(String str, MKVParserHandler handler) {
        int start = STATE_KSTART;
        char quote = '\0';

        char c;

        String key = null;
        String value = null;

        for (int i = 0; i < str.length(); i++) {

            c = str.charAt(i);

            switch (state) {
            case STATE_KSTART:
                if (isKvSeperator(c)) {
                    // have not key
                    return false;
                }

                if (isEmptyChar(c) || isFieldSeperator(c)) {
                    // skip empty
                    start = i + 1;
                    continue;
                }

                if (isQuote(c)) {
                    // start quote
                    quote = c;
                    start = i + 1;
                    state = STATE_QKEY;
                    break;
                }

                start = i;
                state = STATE_KEY;
                break;
            case STATE_KEY:
                if (isKvSeperator(c)) {
                    key = str.substring(start, i).trim();
                    state = STATE_VSTART;
                    break;
                }

                break;
            case STATE_QKEY:
                if (quote == c) {
                    key = str.substring(start, i);
                    state = STATE_SEP;
                    break;
                }
                break;
            case STATE_SEP:
                if (isEmptyChar(c)) {
                    break;
                }

                if (isKvSeperator(c)) {
                    state = STATE_VSTART;
                    break;
                }

                state = STATE_VSTART;
                break;
            case STATE_VSTART:
                if (isKvSeperator(c)) {
                    // have not key
                    return false;
                }

                if (isEmptyChar(c)) {
                    // skip empty
                    start = i + 1;
                    continue;
                }

                if (isQuote(c)) {
                    // start quote
                    quote = c;
                    start = i + 1;
                    state = STATE_QVALUE;
                    break;
                }

                start = i;
                state = STATE_VALUE;
                break;
            case STATE_VALUE:
                if (isFieldSeperator(c)) {
                    value = str.substring(start, i).trim();

                    handler.onKvPaire(key, value);
                    state = STATE_KSTART;
                    key = null;
                    value = null;
                }

                break;
            case STATE_QVALUE:
                if (quote == c) {
                    value = str.substring(start, i);
                    state = STATE_KSTART;

                    handler.onKvPaire(key, value);
                    key = null;
                    value = null;
                    break;
                }

                break;
            }

        }

        if (key != null && state == STATE_VALUE) {
            value = str.substring(start);

            handler.onKvPaire(key, value);
        }

        return true;
    }
}
