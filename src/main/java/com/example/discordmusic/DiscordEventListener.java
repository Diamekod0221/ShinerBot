package com.example.discordmusic;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DiscordEventListener {

    @Autowired
    private MessageCreateListener messageListener;

    @EventListener
    public Mono<Void> onMessageCreate(MessageCreateEvent event) {
        return messageListener.execute(event);
    }
}
