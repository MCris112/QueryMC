package com.darkredgm.querymc.Exceptions;

public class IllegalAccessWithoutKey extends RuntimeException {
    public IllegalAccessWithoutKey() {
        super("Cannot use the primary key because has no primary key");
    }
}
