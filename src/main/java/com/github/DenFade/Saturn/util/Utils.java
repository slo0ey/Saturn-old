package com.github.DenFade.Saturn.util;

public class Utils {

    private Utils(){

    }

    public static int random(int min, int max){
        return ((int) Math.floor(Math.random()*(max-min+1)))+min;
    }

    public static int safeInt(String num, int replacer){
        try{
            return Integer.parseInt(num);
        } catch (NumberFormatException e){
            return replacer;
        }
    }
}
