package com.example.discordmusic;

import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Set;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    @Autowired
    BotConfig botConfig;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    public boolean checkCommand(Event event){

        Set<String> allowedCommands = botConfig.getAllowedCommands();
        try
        {MessageCreateEvent eventMessage = (MessageCreateEvent) event;
            String command = eventMessage.getMessage().getContent().split(" ")[0].strip().toLowerCase();
            return allowedCommands.contains(command);}
        catch (RuntimeException e) {
            throw new RuntimeException("Wrong event type - expected MessageCreateEvent, got: " + event.getClass());
        }
    }


    public Mono<Void> processCommand(MessageCreateEvent eventMessage) {
        String messageContent = eventMessage.getMessage().getContent();
        String commandName = Arrays.asList(messageContent.split(" ")).get(0);
        Set<String> allowedCommands = botConfig.getAllowedCommands();

        if (allowedCommands.contains(commandName)) {
            return Flux.fromIterable(allowedCommands)
                    .filter(key -> messageContent.startsWith('!' + key))
                    .flatMap(entry -> botConfig.getCommandAction(entry).execute(eventMessage))
                    .next()
                    .then(); // Convert to Mono<Void>
        } else {
            // Handle unknown command
            return eventMessage.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage("Unknown command!"))
                    .then();
        }
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        if(checkCommand(event)){
        return processCommand(event);}
        else
        {throw new IllegalArgumentException("no such command");}
    }

    public MessageCreateListener(){
        System.out.println("CreateListener online");
    }

}