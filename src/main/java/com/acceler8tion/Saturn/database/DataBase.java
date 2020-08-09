package com.acceler8tion.Saturn.database;

import com.acceler8tion.Saturn.database.minesweeper.MineSweeperChannelDB;
import com.acceler8tion.Saturn.database.server.PunishedUserDB;
import com.acceler8tion.Saturn.database.server.ServerConfigurationDB;

public class DataBase {

    private DataBase(){

    }

    public static final ServerConfigurationDB serverConfigurationDB = new ServerConfigurationDB();
    public static final PunishedUserDB punishedUserDB = new PunishedUserDB();
    public static final MineSweeperChannelDB minesweeperChannelDB = new MineSweeperChannelDB();
}
