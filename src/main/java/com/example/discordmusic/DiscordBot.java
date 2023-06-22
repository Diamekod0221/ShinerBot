package com.example.discordmusic;


import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import static org.springframework.core.io.support.SpringFactoriesLoader.FailureHandler.handleMessage;

public class DiscordBot {
    private final GatewayDiscordClient client;
    private final MusicPlayer musicPlayer;

    public DiscordBot(String token){
        client = DiscordClient.create(token).login().block();
        musicPlayer = new MusicPlayer();
    }

    public void start(){
        client.getEventDispatcher().on(MessageCreateEvent.class).subscribe(event -> handleMessage(event));

    }

    private void handleMessage(MessageCreateEvent event){
        Message message = event.getMessage();
        String content = message.getContent();

        //TODO: switch on option

        if(content.startsWith("!play")){
            String query = content.substring("!play".length()).trim();
            musicPlayer.play(query);
            //TODO: add playing logic
        }



    }
}
