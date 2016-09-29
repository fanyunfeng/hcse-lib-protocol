package com.hcse.util.mkv;

public class MKVParser {
    final int STATE_KSTART = 0;
    final int STATE_KEY = 1;
    final int STATE_QKEY = 2;

    final int STATE_SEP = 3;

    final int STATE_VSTART = 4;
    final int STATE_VALUE = 5;
    final int STATE_QVALUE = 6;
    final int STATE_TRANS = 7;

    private int state = STATE_KSTART;

    private String quote = "'\"";

    private String fieldSeperator = ",; ";

    private String kvSeperator = ":=";

    private String emptyChar = " \t";

    private int oldState = 0;

    private StringBuilder buffer;

    public MKVParser() {
        buffer = new StringBuilder();
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

    private String getFieldString() {
        String ret = buffer.toString().trim();

        buffer.delete(0, buffer.length());

        return ret;
    }

    public boolean parse(String str, MKVParserHandler handler) {
        char quote = '\0';

        char c;

        String key = null;
        String value = null;

        for (int i = 0; i < str.length(); i++) {

            c = str.charAt(i);

            if (c == '\\') {
                oldState = state;

                state = STATE_TRANS;
                continue;
            }

            switch (state) {
            case STATE_TRANS:
                switch (c) {
                case '\'':
                case '\"':
                case '\\':
                case '/':
                    buffer.append(c);
                    state = oldState;
                    break;
                default:
                    return false;
                }
                break;
            case STATE_KSTART:
                if (isKvSeperator(c)) {
                    // have not key
                    return false;
                }

                if (isEmptyChar(c) || isFieldSeperator(c)) {
                    continue;
                }

                if (isQuote(c)) {
                    // start quote
                    quote = c;
                    state = STATE_QKEY;
                    break;
                }

		buffer.append(c);
                state = STATE_KEY;
                break;
            case STATE_KEY:
                if (isKvSeperator(c)) {
                    key = getFieldString();
                    state = STATE_VSTART;
                    break;
                }

                buffer.append(c);
                break;
            case STATE_QKEY:
                if (quote == c) {
                    key = getFieldString();
                    state = STATE_SEP;
                    break;
                }

                buffer.append(c);
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
                    break;
                }

                if (isQuote(c)) {
                    // start quote
                    quote = c;
                    state = STATE_QVALUE;
                    break;
                }

                buffer.append(c);
                state = STATE_VALUE;
                break;
            case STATE_VALUE:
                if (isFieldSeperator(c)) {
                    value = getFieldString();

                    handler.onKvPaire(key, value);
                    state = STATE_KSTART;
                    key = null;
                    value = null;
                }

                buffer.append(c);
                break;
            case STATE_QVALUE:
                if (quote == c) {
                    value = getFieldString();
                    state = STATE_KSTART;

                    handler.onKvPaire(key, value);
                    key = null;
                    value = null;
                    break;
                }

                buffer.append(c);
                break;
            }
        }

        if (key != null && state == STATE_VALUE) {
            value = getFieldString();

            handler.onKvPaire(key, value);
        }

        return true;
    }
}
