package com.acceler8tion.Saturn.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;

public class Translator {

    private static final Map<String, Properties> translator = new HashMap<>();

    private Translator(){

    }

    public static void register(Language lang) throws IOException {
        Properties prop = new Properties();
        String l = lang.getLang();
        prop.load(Translator.class.getResourceAsStream(String.format("/%s_script.properties", l)));
        translator.put(l, prop);
    }

    public static <T> void doOnTranslate(Language lang, String key, T what, BiConsumer<String, T> translate){
        String l = lang.getLang();
        Properties prop = translator.get(l);
        String script;
        try {
            script = new String(prop.getProperty(key).getBytes(StandardCharsets.ISO_8859_1), "euc-kr");
        } catch (UnsupportedEncodingException e) {
            script = "---";
        }
        translate.accept(script, what);
    }

    public static <T> void doOnTranslate(Language lang, String[] keys, T what, BiConsumer<String[], T> translate){
        String l = lang.getLang();
        Properties prop = translator.get(l);
        String[] script = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            try {
                script[i] = new String(prop.getProperty(keys[i]).getBytes(StandardCharsets.ISO_8859_1), "euc-kr");
            } catch (UnsupportedEncodingException e){
                script[i] = "---";
            }
        }
        translate.accept(script, what);
    }

    public static int getSupportLanguageCount(){
        return translator.size();
    }

    public static Set<String> keySet(){
        return translator.keySet();
    }

    public enum Language{

        KR("KR"),
        EN("EN");

        private final String lang;

        Language(String lang){
            this.lang = lang;
        }

        public String getLang() {
            return lang;
        }

        public static Language keyToLang(String key){
            Language lang = null;
            for(Language l : values()){
                if(l.getLang().equals(key)) lang = l;
            }
            return lang;
        }
    }

}
