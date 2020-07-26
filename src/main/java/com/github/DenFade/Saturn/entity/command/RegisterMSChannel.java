package com.github.DenFade.Saturn.entity.command;

import com.github.DenFade.Saturn.center.DataBase;
import com.github.DenFade.Saturn.entity.IGuildCommand;
import com.github.DenFade.Saturn.entity.annotation.CommandDoc;
import com.github.DenFade.Saturn.entity.annotation.CommandProperties;
import com.github.DenFade.Saturn.event.IGuildMessageReceivedEvent;
import com.github.DenFade.Saturn.util.EmojiFactory;
import com.github.DenFade.Saturn.util.PermissionLevel;

import net.dv8tion.jda.api.entities.Guild;

import java.util.Collections;
import java.util.Objects;

@CommandProperties(level = PermissionLevel.ADMIN)
@CommandDoc(
        alias = "ms.on",
        preview = "해당 채널에서 지뢰찾기 활성화"
)
public class RegisterMSChannel extends IGuildCommand {

    @Override
    public void run(IGuildMessageReceivedEvent event) {
        super.run(event);

        Guild g = event.getTextChannel().getGuild();
        boolean b = Objects.requireNonNull(
                DataBase.minesweeperChannelDB.extract(db -> {
                    return !db.isNull(g.getId()) && db.getJSONArray(g.getId()).toList().contains(event.getTextChannel().getId());
                }));
        System.out.println("sbffasf");
        if(b){
            event.getTextChannel().sendMessage(
                    String.format("이미 등록된 채널 입니다. %s ", EmojiFactory.CONFUSED_FACE.getEmoji())
            ).queue(message ->  message.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
        } else {
            event.getTextChannel().sendMessage(
                    String.format("이제 #%s 에서 지뢰찾기를 플레이 할 수 있어요! %s", event.getGuildChannel().getName(), EmojiFactory.GRINNING_FACE.getEmoji())
            ).queue(message ->  message.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            DataBase.minesweeperChannelDB.update(db -> {
                if(db.isNull(g.getId())) db.put(g.getId(), Collections.singletonList(event.getTextChannel().getId()));
                else db.getJSONArray(g.getId()).put(event.getTextChannel().getId());

                return db;
            });
        }
    }
}
