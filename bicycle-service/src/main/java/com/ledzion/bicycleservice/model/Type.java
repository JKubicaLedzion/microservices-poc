package com.ledzion.bicycleservice.model;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Type {

    CITY("City"),
    ROAD("Road"),
    CROSS("Cross"),
    MOUNTAIN("Mountain"),
    BMX("Bmx"),
    ELECTRIC("Electric");

    private String typeDescription;

    Type(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public static Type getType(String typeDescription) {
        return Arrays.stream(Type.values())
                .filter(t -> typeDescription.equals(t.getTypeDescription()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Wrong type provided."));
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    @Override
    public String toString() {
        return getTypeDescription();
    }
}
