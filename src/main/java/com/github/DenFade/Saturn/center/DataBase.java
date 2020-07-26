package com.github.DenFade.Saturn.center;

import com.github.DenFade.Saturn.database.MineSweeperChannelDB;
import com.github.DenFade.Saturn.database.PunishedUserDB;
import com.github.DenFade.Saturn.database.ServerConfigurationDB;

public class DataBase {

    private DataBase(){

    }

    public static final ServerConfigurationDB serverConfigurationDB = new ServerConfigurationDB();
    public static final PunishedUserDB punishedUserDB = new PunishedUserDB();
    public static final MineSweeperChannelDB minesweeperChannelDB = new MineSweeperChannelDB();
}
