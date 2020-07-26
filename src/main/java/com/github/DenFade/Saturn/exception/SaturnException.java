package com.github.DenFade.Saturn.exception;

import javax.annotation.Nonnull;

public class SaturnException extends Exception{

    private String cause;

    public SaturnException(){
        super();
    }

    public SaturnException(String cause){
        super(cause);
    }

    @Nonnull
    @Override
    public String getMessage() {
        return cause;
    }

}
