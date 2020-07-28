package com.github.DenFade.Saturn.entity.command.minesweeper;

import com.github.DenFade.Saturn.Bot;
import com.github.DenFade.Saturn.database.DataBase;
import com.github.DenFade.Saturn.entity.annotation.command.CommandDoc;
import com.github.DenFade.Saturn.entity.annotation.command.CommandProperties;
import com.github.DenFade.Saturn.entity.command.IGuildCommand;
import com.github.DenFade.Saturn.entity.game.MineSweeper;
import com.github.DenFade.Saturn.event.IGuildMessageReceivedEvent;
import com.github.DenFade.Saturn.util.EmojiFactory;
import com.github.DenFade.Saturn.util.PermissionLevel;
import com.github.DenFade.Saturn.util.Translator;
import com.github.DenFade.Saturn.util.Utils;

import java.util.Objects;

@CommandProperties(level = PermissionLevel.VERIFIED)
@CommandDoc(
        alias = "ms.stop",
        preview = "진행중인 게임 취소"
)
@SuppressWarnings("ConstantConditions")
public class InterruptCurrentMineSweeper extends IGuildCommand {

    @Override
    public void run(IGuildMessageReceivedEvent event) {
        super.run(event);

        String mergedIds = event.getConfiguration().getId() + event.getTextChannel().getId();
        String userId = event.getUser().getId();
        boolean b = Objects.requireNonNull(
                DataBase.minesweeperChannelDB.extract(db -> {
                    return !db.isNull(event.getConfiguration().getId()) && db.getJSONArray(event.getConfiguration().getId()).toList().contains(event.getTextChannel().getId());
                }));
        if(b){
            if (Bot.minesweeperCenter().has(mergedIds)) {
                final MineSweeper ms = Bot.minesweeperCenter().find(mergedIds);
                if(ms.getOwnerId().equals(userId)){
                    final String messageId = Bot.minesweeperCenter().find(mergedIds).getMessageId();
                    Translator.doOnTranslate(event.getConfiguration().getLang(), new String[]{"ms_stop_endgame", "ms_ongoing_script"}, event, (s, ievent) -> {
                        ievent.getTextChannel().sendMessage(String.format(s[0], EmojiFactory.CONFUSED_FACE.getEmoji())).queue(m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
                        ievent.getTextChannel().deleteMessageById(messageId).queue();
                        ievent.getTextChannel().sendMessage(String.format(s[1], ms.getX(), ms.getY(), ms.getBomb(), EmojiFactory.ANGRY_FACE_WITH_HORNS.getEmoji(), ms.getRate(), "CANCELED", ms.display(MineSweeper.Display.ONGOING, 0, 0), ms.getLeftBomb(), ms.getParticipants().size(), Utils.timeIndicator(System.currentTimeMillis() - ms.getStartAt()))).queue();

                        Bot.minesweeperCenter().unregister(mergedIds);
                    });
                } else {
                    Translator.doOnTranslate(event.getConfiguration().getLang(), "ms_stop_no_permission", event, (s, ievent) -> {
                        ievent.getTextChannel().sendMessage(String.format(s, EmojiFactory.ANGRY_FACE.getEmoji())).queue(m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
                    });
                }
            } else {
                Translator.doOnTranslate(event.getConfiguration().getLang(), "ms_open_no_game", event, (s, ievent) -> {
                    ievent.getTextChannel().sendMessage(String.format(s, EmojiFactory.CONFUSED_FACE.getEmoji())).queue(m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
                });
            }
        } else {
            Translator.doOnTranslate(event.getConfiguration().getLang(), "ms_start_unregistered_channel", event, (s, ievent) -> {
                ievent.getTextChannel().sendMessage(String.format(s, ievent.getGuildChannel().getId(), EmojiFactory.SAD_BUT_RELIEVED_FACE.getEmoji())).queue(m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            });
        }
    }
}
