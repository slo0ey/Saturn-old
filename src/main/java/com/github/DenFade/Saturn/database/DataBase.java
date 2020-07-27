package com.github.DenFade.Saturn.database;

import com.github.DenFade.Saturn.database.minesweeper.MineSweeperChannelDB;
import com.github.DenFade.Saturn.database.server.PunishedUserDB;
import com.github.DenFade.Saturn.database.server.ServerConfigurationDB;

public class DataBase {

    private DataBase(){

    }

    public static final ServerConfigurationDB serverConfigurationDB = new ServerConfigurationDB();
    public static final PunishedUserDB punishedUserDB = new PunishedUserDB();
    public static final MineSweeperChannelDB minesweeperChannelDB = new MineSweeperChannelDB();
}
