package com.ledzion.bicycleservice.model;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Size {

    S("Small"),
    M("Medium");

    private String sizeDescription;

    Size(String sizeDescription) {
        this.sizeDescription = sizeDescription;
    }

    public static Size getSize(String sizeDescription) {
        return Arrays.stream(Size.values())
                .filter(s -> sizeDescription.equals(s.getSizeDescription()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Wrong size rate provided."));


    }

    public String getSizeDescription() {
        return sizeDescription;
    }

    @Override
    public String toString() {
        return getSizeDescription();
    }
}
