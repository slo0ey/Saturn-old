package com.acceler8tion.Saturn.util;

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

    public static String timeIndicator(long mills){
        if(mills < 60*1000){
            return mills/1000 + "s";
        } else if(mills < 60*60*1000){
            return mills/60_000 + "m";
        } else if(mills < 24*60*60*1000){
            return mills/3_600_000 + "h";
        } else {
            return mills/86_400_000 + "d";
        }
    }
}
