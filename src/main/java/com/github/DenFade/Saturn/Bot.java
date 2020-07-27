package com.github.DenFade.Saturn;

import com.github.DenFade.Saturn.center.MineSweeperCenter;
import com.github.DenFade.Saturn.database.DataBase;
import com.github.DenFade.Saturn.center.GuildCommandCenter;
import com.github.DenFade.Saturn.center.RemovableMessageCenter;

import java.util.List;

public class Bot {

    //Main Properties
    public static final String PREFIX = "!!";
    public static final String HELP = "??";
    public static final long DEV = 358394297067896833L;
    public static long BOT;

    //Centers
    private static final GuildCommandCenter commands = new GuildCommandCenter();
    private static final RemovableMessageCenter removableMessages = new RemovableMessageCenter();
    private static final MineSweeperCenter minesweepers = new MineSweeperCenter();

    public static GuildCommandCenter commandCenter(){
        return commands;
    }

    public static RemovableMessageCenter removableMessageCenter(){
        return removableMessages;
    }

    public static MineSweeperCenter minesweeperCenter(){
        return minesweepers;
    }

    public static List<Object> getPunishedUsers(String server){
        return DataBase.punishedUserDB.getUsersFromServer(server);
    }

}
