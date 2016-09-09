package com.hcse.protocol.dump;

import org.codehaus.jackson.util.DefaultPrettyPrinter;

public class JsonPrettyPrinter extends DefaultPrettyPrinter {
    public JsonPrettyPrinter() {
        this._arrayIndenter = new Lf2SpacesIndenter();
    }
}
