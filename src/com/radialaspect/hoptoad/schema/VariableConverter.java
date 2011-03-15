package com.radialaspect.hoptoad.schema;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class VariableConverter implements Converter {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(final Class klass) {
        return (klass == null) ? false : klass.equals(Variable.class);
    }

    @Override
    public void marshal(final Object value, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        final Variable variable = (Variable) value;
        writer.addAttribute("key", variable.getKey());
        writer.setValue(variable.getValue());
    }

    @Override
    public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
        return null;
    }
}
