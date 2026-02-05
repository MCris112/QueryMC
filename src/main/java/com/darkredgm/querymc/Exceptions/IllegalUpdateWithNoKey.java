package com.darkredgm.querymc.Exceptions;

public class IllegalUpdateWithNoKey extends RuntimeException {
    public IllegalUpdateWithNoKey() {
        super("Cannot update model without primary key");
    }
}
