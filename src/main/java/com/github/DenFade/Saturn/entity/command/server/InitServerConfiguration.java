package com.github.DenFade.Saturn.entity.command.server;

import com.github.DenFade.Saturn.database.DataBase;
import com.github.DenFade.Saturn.database.server.ServerConfigurationDB;
import com.github.DenFade.Saturn.entity.annotation.command.CommandDoc;
import com.github.DenFade.Saturn.entity.annotation.command.CommandProperties;
import com.github.DenFade.Saturn.entity.command.IGuildCommand;
import com.github.DenFade.Saturn.event.IGuildMessageReceivedEvent;
import com.github.DenFade.Saturn.util.EmojiFactory;
import com.github.DenFade.Saturn.util.PermissionLevel;
import com.github.DenFade.Saturn.util.Translator;
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
