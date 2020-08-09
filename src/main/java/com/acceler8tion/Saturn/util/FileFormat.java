package com.acceler8tion.Saturn.util;

public enum FileFormat {

    TXT(".txt"),
    JSON(".json"),
    PROPERTIES(".properties"),
    NO("");

    private final String form;

    FileFormat(String form){
        this.form = form;
    }

    public String getForm(){
        return form;
    }

}
