package com.acceler8tion.Saturn.database.minesweeper;

import com.acceler8tion.Saturn.database.IDB;
import com.acceler8tion.Saturn.util.FileFormat;
import com.acceler8tion.Saturn.util.FileStream;

import org.json.JSONObject;

import java.io.IOException;

public class MineSweeperChannelDB implements IDB<JSONObject> {

    private JSONObject db;

    public MineSweeperChannelDB(){
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
        return "database\\mschanneldb";
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
