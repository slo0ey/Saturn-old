package com.acceler8tion.Saturn.entity.command.minesweeper;

import com.acceler8tion.Saturn.database.DataBase;
import com.acceler8tion.Saturn.entity.annotation.command.CommandDoc;
import com.acceler8tion.Saturn.entity.annotation.command.CommandProperties;
import com.acceler8tion.Saturn.entity.command.IGuildCommand;
import com.acceler8tion.Saturn.event.IGuildMessageReceivedEvent;
import com.acceler8tion.Saturn.util.EmojiFactory;
import com.acceler8tion.Saturn.util.PermissionLevel;

import com.acceler8tion.Saturn.util.Translator;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Collections;
import java.util.Objects;

@CommandProperties(level = PermissionLevel.ADMIN)
@CommandDoc(
        alias = "ms.on",
        preview = "해당 채널에서 지뢰찾기 활성화"
)
public class RegisterMineSweeperChannel extends IGuildCommand {

    @Override
    public void run(IGuildMessageReceivedEvent event) {
        super.run(event);

        Guild g = event.getTextChannel().getGuild();
        boolean b = Objects.requireNonNull(
                DataBase.minesweeperChannelDB.extract(db -> {
                    return !db.isNull(g.getId()) && db.getJSONArray(g.getId()).toList().contains(event.getTextChannel().getId());
                }));
        if(b){
            Translator.doOnTranslate(event.getConfiguration().getLang(), "ms_on_already_registered", event, (s, ievent) -> {
                ievent.getTextChannel().sendMessage(
                        String.format(s, EmojiFactory.CONFUSED_FACE.getEmoji())
                ).queue(message ->  message.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            });
        } else {
            Translator.doOnTranslate(event.getConfiguration().getLang(), "ms_on_new_register", event, (s, ievent) -> {
                ievent.getTextChannel().sendMessage(
                        String.format(s, ievent.getGuildChannel().getId(), EmojiFactory.GRINNING_FACE.getEmoji())
                ).queue(message ->  message.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            });
            DataBase.minesweeperChannelDB.update(db -> {
                if(db.isNull(g.getId())) db.put(g.getId(), Collections.singletonList(event.getTextChannel().getId()));
                else db.getJSONArray(g.getId()).put(event.getTextChannel().getId());

                return db;
            });
        }
    }
}
