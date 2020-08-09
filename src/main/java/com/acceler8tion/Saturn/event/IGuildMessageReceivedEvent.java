package com.acceler8tion.Saturn.event;

import com.acceler8tion.Saturn.Bot;
import com.acceler8tion.Saturn.database.DataBase;
import com.acceler8tion.Saturn.database.server.ServerConfigurationDB;
import com.acceler8tion.Saturn.exception.NegativeUserException;
import com.acceler8tion.Saturn.exception.NoSuchCommandException;
import com.acceler8tion.Saturn.exception.NoSuchPermissionException;
import com.acceler8tion.Saturn.exception.UnverifiedServerException;
import com.acceler8tion.Saturn.entity.command.IGuildCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class IGuildMessageReceivedEvent extends IEvent{

    private final GuildMessageReceivedEvent event;
    private final Message message;
    private final User user;
    private final Member member;
    private final TextChannel textChannel;
    private final GuildChannel guildChannel;
    private final String[] splitted;
    private final String alias;

    private IGuildCommand command;
    private BiConsumer<Throwable, IGuildMessageReceivedEvent> handler;
    private Consumer<IGuildMessageReceivedEvent> success;
    private ServerConfigurationDB.ServerConfiguration configuration;

    private IGuildMessageReceivedEvent(GuildMessageReceivedEvent event, Message message, User user, Member member, TextChannel textChannel, GuildChannel guildChannel, String[] splitted, String alias){
        this.event = event;
        this.message = message;
        this.user = user;
        this.member = member;
        this.textChannel = textChannel;
        this.guildChannel = guildChannel;
        this.splitted = splitted;
        this.alias = alias;
    }

    public static IGuildMessageReceivedEvent sync(GuildMessageReceivedEvent event, Message message, User user, Member member, TextChannel textChannel) {
        GuildChannel guildChannel = event.getGuild().getGuildChannelById(textChannel.getId());
        String[] splitted = message.getContentRaw().split(" ");
        String alias = splitted[0].replace(Bot.PREFIX, "");

        return new IGuildMessageReceivedEvent(event, message, user, member, textChannel, guildChannel, splitted, alias);
    }

    public IGuildMessageReceivedEvent scanIfNegativeUser(Function<User, Boolean> scanner) throws NegativeUserException {
        boolean negative = scanner.apply(user);
        if(negative) throw new NegativeUserException();
        return this;
    }

    public IGuildMessageReceivedEvent scanIfVerifiedServer(String serverId) throws UnverifiedServerException {
        boolean unverified = DataBase.serverConfigurationDB.extract(db -> db.isNull(serverId));
        if(!alias.equals("saturn.init") && unverified) throw new UnverifiedServerException();
        else {
            if(!unverified) this.configuration = DataBase.serverConfigurationDB.toServer(serverId);
        }
        return this;
    }

    public IGuildMessageReceivedEvent scanAvailableCommand() throws NoSuchCommandException {
        this.command = Bot.commandCenter().find(alias);
        if(command == null) throw new NoSuchCommandException();
        return this;
    }

    public IGuildMessageReceivedEvent scanIfNoPermission(Function<IGuildMessageReceivedEvent, Boolean> scanner) throws NoSuchPermissionException {
        boolean no_permission = scanner.apply(this);
        if(no_permission) throw new NoSuchPermissionException();
        return this;
    }

    public IGuildMessageReceivedEvent onError(BiConsumer<Throwable, IGuildMessageReceivedEvent> callback){
        handler = callback;
        return this;
    }

    public IGuildMessageReceivedEvent onSuccess(Consumer<IGuildMessageReceivedEvent> callback){
        success = callback;
        return this;
    }

    public void execute(){
        try{
            command.run(this);
        } catch (Exception e){
            handler.accept(e, this);
            return;
        }
        success.accept(this);
    }

    public Message getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Member getMember() {
        return member;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public GuildChannel getGuildChannel() {
        return guildChannel;
    }

    public IGuildCommand getCommand() {
        return command;
    }

    public String[] getSplitted() {
        return splitted;
    }

    public ServerConfigurationDB.ServerConfiguration getConfiguration(){
        return configuration;
    }
}
