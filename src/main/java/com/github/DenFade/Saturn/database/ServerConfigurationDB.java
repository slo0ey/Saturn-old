package com.github.DenFade.Saturn.database;

import com.github.DenFade.Saturn.util.FileFormat;
import com.github.DenFade.Saturn.util.FileStream;
import com.github.DenFade.Saturn.util.Translator;
import org.json.JSONObject;

import java.io.IOException;

public class ServerConfigurationDB implements IDB<JSONObject> {

    private JSONObject db;

    public ServerConfigurationDB(){
        JSONObject db1;
        try{
            db1 =  new JSONObject(FileStream.read(getPath() + format().getForm()));
        } catch (IOException e){
            db1 = null;
        }
        this.db = db1 == null ? init() : db1;
        sync();
    }

    @Override
    public JSONObject init() {
        return new JSONObject();
    }

    @Override
    public JSONObject getDB() {
        return db;
    }

    @Override
    public void setDB(JSONObject db) {
        this.db = db;
    }

    @Override
    public String getPath() {
        return "database\\serverconfigurationdb";
    }

    @Override
    public FileFormat format() {
        return FileFormat.JSON;
    }

    @Override
    public String convert() {
        return getDB().toString();
    }

    public ServerConfiguration toServer(String serverId){
        return new ServerConfiguration(getDB(), serverId);
    }

    public static class ServerConfiguration {

        private final Translator.Language lang;

        ServerConfiguration(JSONObject server, String serverId){
            this.lang = Translator.Language.keyToLang(server.getJSONObject(serverId).getString("language"));
        }

        public Translator.Language getLang() {
            return lang;
        }
    }
}
