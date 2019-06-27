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

    public String getSizeDescription() {
        return sizeDescription;
    }

    public Size getSize(String sizeDescription) {
        return Arrays.stream(Size.values())
                .filter(s -> sizeDescription.equals(s.getSizeDescription()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Wrong size rate provided."));


    }
}
