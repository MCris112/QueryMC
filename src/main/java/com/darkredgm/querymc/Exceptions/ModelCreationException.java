package com.darkredgm.querymc.Exceptions;

public class ModelCreationException extends RuntimeException {
    public ModelCreationException() {
        super("No se pudo crear el modelo");
    }
}
