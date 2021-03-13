package com.packt.microprofile.book.ch4.entityandparamproviders;

import javax.ws.rs.ext.ParamConverter;

public class ColorParamConverter implements ParamConverter<Color> {

    @Override
    public Color fromString(String value) {
        return Color.valueOf(value.toUpperCase());
    }

    @Override
    public String toString(Color value) {
        return value.name();
    }
}
