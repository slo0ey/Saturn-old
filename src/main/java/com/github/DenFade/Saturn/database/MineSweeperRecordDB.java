package com.github.DenFade.Saturn.database;

import com.github.DenFade.Saturn.util.FileFormat;
import org.json.JSONArray;

public class MineSweeperRecordDB  implements  IDB<JSONArray> {

    private JSONArray db;

    public MineSweeperRecordDB(){

    }

    @Override
    public JSONArray init() {
        return new JSONArray();
    }

    @Override
    public JSONArray getDB() {
        return null;
    }

    @Override
    public void setDB(JSONArray db) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public FileFormat format() {
        return null;
    }

    @Override
    public String convert() {
        return null;
    }

}
