package com.acceler8tion.Saturn.entity.command.server;

import com.acceler8tion.Saturn.database.DataBase;
import com.acceler8tion.Saturn.util.EmojiFactory;
import com.acceler8tion.Saturn.database.server.ServerConfigurationDB;
import com.acceler8tion.Saturn.entity.annotation.command.CommandDoc;
import com.acceler8tion.Saturn.entity.annotation.command.CommandProperties;
import com.acceler8tion.Saturn.entity.command.IGuildCommand;
import com.acceler8tion.Saturn.event.IGuildMessageReceivedEvent;
import com.acceler8tion.Saturn.util.PermissionLevel;
import com.acceler8tion.Saturn.util.Translator;
import net.dv8tion.jda.api.entities.Guild;

import java.util.concurrent.TimeUnit;

@CommandProperties(level = PermissionLevel.ADMIN)
@CommandDoc(
        alias = "saturn.init",
        preview = "서버 configuration 생성"
)
public class InitServerConfiguration extends IGuildCommand {

    @Override
    public void run(IGuildMessageReceivedEvent event) {
        super.run(event);

        Guild server = event.getTextChannel().getGuild();
        boolean unverified = DataBase.serverConfigurationDB.extract(db -> db.isNull(server.getId()));
        if(unverified){
            ServerConfigurationDB.ServerConfiguration config = DataBase.serverConfigurationDB.registerServer(server.getId(), server.getOwnerId(), Translator.Language.EN);
            Translator.doOnTranslate(Translator.Language.EN, new String[]{"saturn_init_successful", "saturn_init_successful_after"}, event, (s, ievent) -> {
                ievent.getTextChannel().sendMessage(s[0]).queue();
                ievent.getTextChannel().sendMessage(s[1]).queueAfter(3000, TimeUnit.MILLISECONDS, m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            });
        } else {
            Translator.doOnTranslate(Translator.Language.EN, "saturn_init_failed", event, (s, ievent) -> {
                ievent.getTextChannel().sendMessage(s).queue(m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            });
        }

    }

}
