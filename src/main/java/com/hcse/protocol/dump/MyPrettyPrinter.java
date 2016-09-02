package com.hcse.protocol.dump;

import org.codehaus.jackson.util.DefaultPrettyPrinter;

public class MyPrettyPrinter extends DefaultPrettyPrinter {
    public MyPrettyPrinter() {
        this._arrayIndenter = new Lf2SpacesIndenter();
    }
}
