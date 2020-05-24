package com.github.DenFade.Saturn.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class Main {

    private static final String BOT_CONFIGURATION = "setting\\bot_configuration.properties";

    public static void main(String[] args){

        Properties p = new Properties();
        try{
            FileInputStream fis = new FileInputStream(BOT_CONFIGURATION);
            p.load((InputStream) fis);

            JDA jda = JDABuilder.createDefault(p.getProperty("token"))
                    .addEventListeners(new STListener())
                    .setActivity(Activity.of(Activity.ActivityType.DEFAULT, p.getProperty("activity-desc")))
                    .setStatus(OnlineStatus.ONLINE)
                    .build();
            System.out.println("Success to start ;)");

        } catch (Exception e){
            e.printStackTrace();

            System.out.println("Failed to Start..");
            System.exit(1);
        }
    }
}
