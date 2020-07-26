package com.github.DenFade.Saturn.database;

import com.github.DenFade.Saturn.util.FileFormat;
import com.github.DenFade.Saturn.util.FileStream;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PunishedUserDB implements IDB<JSONObject> {

    private JSONObject db;

    public PunishedUserDB(){
        JSONObject db1;
        try{
            db1 =  new JSONObject(FileStream.read(getPath() + format().getForm()));
        } catch (IOException e){
            db1 = null;
        }
        this.db = db1 == null ? init() : db1;
        sync();
    }

    public List<Object> getUsersFromServer(String server){
        return db.isNull(server) ? Collections.emptyList() : db.getJSONArray(server).toList();
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
        return "database\\punisheduserdb";
    }

    @Override
    public FileFormat format() {
        return FileFormat.JSON;
    }

    @Override
    public String convert() {
        return getDB().toString();
    }
}
