package com.github.DenFade.Saturn.main;

import com.github.DenFade.Saturn.ms.MineSweeper;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.io.FileInputStream;
import java.util.*;

public class Main {

    private static final String BOT_CONFIGURATION = "setting\\bot_configuration.properties";

    public static void main(String[] args){

        MineSweeper ms = new MineSweeper(16,16,40);
        Properties p = new Properties();
        try{
            FileInputStream fis = new FileInputStream(BOT_CONFIGURATION);
            p.load(fis);

            JDA jda = JDABuilder.createDefault(p.getProperty("token"))
                    .addEventListeners(new STListener())
                    .setActivity(Activity.of(Activity.ActivityType.DEFAULT, p.getProperty("activity-desc")))
                    .setStatus(OnlineStatus.ONLINE)
                    .build();
            ms.setMine();
            System.err.println(ms.toString());
            System.out.println("Success to start ;)");

        } catch (Exception e){
            e.printStackTrace();

            System.out.println("Failed to Start..");
            System.exit(1);
        }
    }
}
