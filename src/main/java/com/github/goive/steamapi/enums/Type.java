package com.github.goive.steamapi.enums;

public enum Type {
    GAME("game"),
    DLC("dlc"),
    MOVIE("movie"),
    DEMO("demo"),
    UNDEFINED("undefined");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
