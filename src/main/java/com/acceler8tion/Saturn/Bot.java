package com.acceler8tion.Saturn;

import com.acceler8tion.Saturn.database.DataBase;
import com.acceler8tion.Saturn.center.MineSweeperCenter;
import com.acceler8tion.Saturn.center.GuildCommandCenter;
import com.acceler8tion.Saturn.center.RemovableMessageCenter;

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
