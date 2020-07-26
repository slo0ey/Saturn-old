package com.github.DenFade.Saturn.entity.command;

import com.github.DenFade.Saturn.center.DataBase;
import com.github.DenFade.Saturn.entity.IGuildCommand;
import com.github.DenFade.Saturn.entity.annotation.CommandDoc;
import com.github.DenFade.Saturn.entity.annotation.CommandProperties;
import com.github.DenFade.Saturn.event.IGuildMessageReceivedEvent;
import com.github.DenFade.Saturn.util.EmojiFactory;
import com.github.DenFade.Saturn.util.PermissionLevel;
import net.dv8tion.jda.api.entities.Guild;
import org.json.JSONArray;

import java.util.Collections;
import java.util.Objects;

@CommandProperties(level = PermissionLevel.ADMIN)
@CommandDoc(
        alias = "ms.off",
        preview = "해당 채널에서 지뢰찾기 비활성화"
)
public class UnregisterMSChannel extends IGuildCommand {

    @Override
    public void run(IGuildMessageReceivedEvent event) {
        super.run(event);

        Guild g = event.getTextChannel().getGuild();
        boolean b = Objects.requireNonNull(
                DataBase.minesweeperChannelDB.extract(db -> {
                    return !db.isNull(g.getId()) && db.getJSONArray(g.getId()).toList().contains(event.getTextChannel().getId());
                }));
        if(!b){
            event.getTextChannel().sendMessage(
                    String.format("이미 비활성화 된 채널 입니다. %s", EmojiFactory.CONFUSED_FACE.getEmoji())
            ).queue(message ->  message.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
        } else {
            event.getTextChannel().sendMessage(
                    String.format("더이상 #%s 에서 지뢰찾기를 플레이 할 수 없습니다! %s", event.getGuildChannel().getName(), EmojiFactory.SAD_BUT_RELIEVED_FACE.getEmoji())
            ).queue(message ->  message.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            DataBase.minesweeperChannelDB.update(db -> {
                int index = db.getJSONArray(g.getId()).toList().indexOf(event.getTextChannel().getId());
                db.getJSONArray(g.getId()).remove(index);

                return db;
            });
        }
    }

}
