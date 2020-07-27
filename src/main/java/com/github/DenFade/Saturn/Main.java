package com.github.DenFade.Saturn;

import com.github.DenFade.Saturn.entity.command.minesweeper.CreateNewMineSweeper;
import com.github.DenFade.Saturn.entity.command.server.ChangeServerLanguage;
import com.github.DenFade.Saturn.entity.command.server.InitServerConfiguration;
import com.github.DenFade.Saturn.entity.command.minesweeper.RegisterMineSweeperChannel;

import com.github.DenFade.Saturn.entity.command.minesweeper.UnregisterMineSweeperChannel;
import com.github.DenFade.Saturn.util.Translator;
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
            logWithTimestamp("Successful injection\n%d Language now support", Translator.size());



            logWithTimestamp("Injecting GuildCommands...");
            Bot.commandCenter()
                    .register("saturn.init", new InitServerConfiguration())
                    .register("saturn.language", new ChangeServerLanguage())
                    .register("ms.on", new RegisterMineSweeperChannel())
                    .register("ms.off", new UnregisterMineSweeperChannel())
                    .register("ms.new", new CreateNewMineSweeper());
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
