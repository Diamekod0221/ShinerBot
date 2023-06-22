package com.example.discordmusic;

import discord4j.core.object.entity.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class MessageListener {

    @Autowired
    private BotConfig botConfig;
    @Autowired
    private MusicPlayer player;
    public String checkCommand(Message eventMessage){
        List<String> allowedCommands = botConfig.getAllowedCommands();
        String command = eventMessage.getContent().split(" ")[0].strip().toLowerCase();
        if(allowedCommands.contains(command)){
            return command;
        }
        else
            return "Command not found";
    }

    public Mono<Void> processCommand(Message eventMessage){

        String message = checkCommand(eventMessage);

        switch (message){
            case "!play":
                return new PlayEventHandler(eventMessage);
              break;
            case "!shiner":
                return Mono.just(eventMessage).filter(mess -> mess.getAuthor().map(user -> !user.isBot()).orElse(false)).flatMap(Message::getChannel)
                        .flatMap(channel -> channel.createMessage("Them connections mighty heavy' sir")).then();
                break;
            case "!disconnect":
                return eventMessage.getChannel().flatMap(Channel::disconnect).then();
                break;
            case "!goodole'south":
                return
                        Mono.just()
                        break;
            default:
                return Mono.just(eventMessage).flatMap(Message::getChannel).flatMap(channel -> channel.createMessage(message)).then();



        }

    }



}
