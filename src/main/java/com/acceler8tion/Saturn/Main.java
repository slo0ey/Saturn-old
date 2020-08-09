package com.acceler8tion.Saturn;

import com.acceler8tion.Saturn.entity.command.minesweeper.*;
import com.acceler8tion.Saturn.entity.command.server.ChangeServerLanguage;
import com.acceler8tion.Saturn.entity.command.server.InitServerConfiguration;

import com.acceler8tion.Saturn.util.Translator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.io.FileInputStream;
import java.util.*;

public class Main {

    private static final String line = "----------------------------------------";

    private static final String BOT_CONFIGURATION = "setting\\bot_configuration.properties";

    public static void main(String[] args){

        try{
            Properties p = new Properties();
            FileInputStream fis = new FileInputStream(BOT_CONFIGURATION);
            p.load(fis);
            Bot.BOT = Long.parseLong(p.getProperty("client"));

            logWithTimestamp("Injecting Translations...");
            Translator.register(Translator.Language.KR);
            Translator.register(Translator.Language.EN);
            logWithTimestamp("Successful injection\n%d Language now support", Translator.getSupportLanguageCount());



            logWithTimestamp("Injecting GuildCommands...");
            Bot.commandCenter()
                    .register("saturn.init", new InitServerConfiguration())
                    .register("saturn.language", new ChangeServerLanguage())
                    .register("ms.on", new RegisterMineSweeperChannel())
                    .register("ms.off", new UnregisterMineSweeperChannel())
                    .register("ms.new", new CreateNewMineSweeper())
                    .register("ms.stop", new InterruptCurrentMineSweeper())
                    .register("ms.o", new OpenTileCurrentMineSweeper())
                    .register("ms.f", new FlaggingTileCurrentMineSweeper())
                    .register("ms.r", new RetrieveCurrentMineSweeper());
            logWithTimestamp("Successful injection\n%d GuildCommand now available", Bot.commandCenter().size());



            logWithTimestamp("Connecting Discord...");
            JDA jda = JDABuilder.createDefault(p.getProperty("token"))
                    .addEventListeners(new SaturnListener())
                    .setActivity(Activity.of(Activity.ActivityType.DEFAULT, p.getProperty("activity-desc")))
                    .setStatus(OnlineStatus.ONLINE)
                    .build();
            logWithTimestamp("Discord client ON");

        } catch (Exception e){
            e.printStackTrace();

            System.out.println("Failed to Start..");
            System.exit(1);
        }
    }

    private static void logWithTimestamp(String text, Object... o){
        System.out.println(String.format(line + "\n" + text + "\nAt :" + System.currentTimeMillis() + "\n" + line, o));
    }
}
