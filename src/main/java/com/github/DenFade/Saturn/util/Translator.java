package com.github.DenFade.Saturn.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
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
        String script = translator.get(l).getProperty(key);
        translate.accept(script, what);
    }

    public static int size(){
        return translator.size();
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
