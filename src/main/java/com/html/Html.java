package com.html;

/**
 * Created by guitarnut on 12/9/17.
 */
public enum Html {
    LINK_ELEMENT("a"),
    HREF_ATTRIBUTE("href");

    private String value;

    Html (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
