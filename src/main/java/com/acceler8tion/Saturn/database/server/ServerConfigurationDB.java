package com.acceler8tion.Saturn.database.server;

import com.acceler8tion.Saturn.database.IDB;
import com.acceler8tion.Saturn.util.FileFormat;
import com.acceler8tion.Saturn.util.FileStream;
import com.acceler8tion.Saturn.util.Translator;
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

    public ServerConfiguration registerServer(String serverId, String ownerId, Translator.Language lang){
        ServerConfiguration config = new ServerConfiguration(serverId, ownerId, lang);
        update(db -> {
            JSONObject server = new JSONObject();
            server.put("ownerId", ownerId);
            server.put("language", lang.getLang());
            db.put(serverId, server);

            return db;
        });
        return config;
    }

    public static class ServerConfiguration {

        private final String id;
        private String ownerId;
        private Translator.Language lang;

        ServerConfiguration(JSONObject server, String serverId){
            JSONObject serverObj = server.getJSONObject(serverId);
            this.id = serverId;
            this.ownerId = serverObj.getString("ownerId");
            this.lang = Translator.Language.keyToLang(serverObj.getString("language"));
        }

        ServerConfiguration(String serverId, String ownerId, Translator.Language lang){
            this.id = serverId;
            this.ownerId = ownerId;
            this.lang = lang;
        }

        public String getId() {
            return id;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public Translator.Language getLang() {
            return lang;
        }
    }
}
