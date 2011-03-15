package com.radialaspect.hoptoad.schema;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("var")
@XStreamConverter(VariableConverter.class)
public class Variable {
    private String key;
    private String value;

    public Variable(final String key, final String value) {
        setKey(key);
        setValue(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        if ((key == null) || "".equals(key.trim())) {
            throw new IllegalArgumentException("key must not be null");
        }

        this.key = key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        if ((value == null) || "".equals(value.trim())) {
            throw new IllegalArgumentException("value must not be null");
        }

        this.value = value.trim();
    }
}
