package com.github.DenFade.Saturn;

import com.github.DenFade.Saturn.entity.annotation.command.CommandProperties;
import com.github.DenFade.Saturn.entity.annotation.command.CommandTest;
import com.github.DenFade.Saturn.event.IGuildMessageReceivedEvent;
import com.github.DenFade.Saturn.exception.NegativeUserException;
import com.github.DenFade.Saturn.exception.NoSuchCommandException;
import com.github.DenFade.Saturn.exception.NoSuchPermissionException;
import com.github.DenFade.Saturn.exception.UnverifiedServerException;
import com.github.DenFade.Saturn.util.EmojiFactory;
import com.github.DenFade.Saturn.util.PermissionLevel;
import com.github.DenFade.Saturn.util.Translator;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.json.JSONObject;

import javax.annotation.Nonnull;

import java.util.List;

public class SaturnListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        Message message = event.getMessage();
        User user = event.getAuthor();
        Member member = event.getMember();
        TextChannel channel = event.getChannel();

        if(!message.getContentRaw().startsWith(Bot.PREFIX) && !message.getContentRaw().startsWith(Bot.HELP)) return;

        try {
            IGuildMessageReceivedEvent.sync(event, message, user, member, channel)
                    .scanIfNegativeUser(User::isBot)
                    .scanIfVerifiedServer(event.getGuild().getId())
                    .scanAvailableCommand()
                    .scanIfNoPermission(e -> {
                        PermissionLevel permissionLevel = e.getCommand().getClass().getAnnotation(CommandProperties.class).level();
                        User u = e.getUser();
                        Member m = e.getMember();
                        boolean no_permission = false;
                        if(e.getUser().getIdLong() != Bot.DEV){
                            switch (permissionLevel) {
                                case ANY:
                                    //All user accessible
                                    break;
                                case VERIFIED:
                                    //All user accessible except punished user
                                    List<Object> list = Bot.getPunishedUsers(e.getTextChannel().getGuild().getId());
                                    no_permission = list.stream().anyMatch(o -> ((JSONObject) o).getString("id").equals(e.getUser().getId()));
                                    break;
                                case ADMIN:
                                    //Only admin accessible
                                    if (!m.isOwner() && m.getRoles().stream().noneMatch(r -> r.hasPermission(Permission.ADMINISTRATOR)))
                                        no_permission = true;
                                    break;
                                case DEVELOPER:
                                    //Only dev accessible
                                    no_permission = true;
                            }
                        }
                        return no_permission;
                    })
                    .onError((e, ev) -> {
                        e.printStackTrace();
                        ev.getTextChannel()
                                .sendMessage(String.format("Type: %s\nDesc: %s", e.getClass().getName(), e.getMessage())).queue();
                    })
                    .onSuccess(ev -> {
                        if(ev.getCommand().getClass().isAnnotationPresent(CommandTest.class)){
                            ev.getTextChannel().sendMessage("Success!").queue();
                        }
                    })
                    .execute();
        } catch (NegativeUserException e) {
            return;
        } catch (UnverifiedServerException e) {
            Translator.doOnTranslate(Translator.Language.EN, "unverified_server", channel, (s, textChannel) -> {
                textChannel.sendMessage(s).queue(m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
            });
        } catch (NoSuchCommandException e) {
            //TODO: Need to handle
        } catch (NoSuchPermissionException e) {
            channel.sendMessage(String.format("**You don't have permission to use!** %s", EmojiFactory.ANGRY_FACE.getEmoji())).queue(m -> m.addReaction(EmojiFactory.WHITE_CHECK_MARK.getEmoji()).queue());
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        super.onGuildMessageReactionAdd(event);

        String msgId = event.getMessageId();
        boolean isCustom = event.getReactionEmote().isEmote();

        if(isCustom){
            //Nope :(
        } else {
            if(event.getReaction().getReactionEmote().getAsCodepoints().equals("U+2705")){
                if(event.getUserIdLong() == Bot.BOT){
                    Bot.removableMessageCenter().register(msgId, event.getUserId());
                } else {
                    if(Bot.removableMessageCenter().find(msgId) != null) {
                        event.getChannel().deleteMessageById(msgId).queue();
                        Bot.removableMessageCenter().unregister(msgId);
                    }
                }
            }
        }

    }
}
