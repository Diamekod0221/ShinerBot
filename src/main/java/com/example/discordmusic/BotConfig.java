package com.example.discordmusic;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class BotConfig {

    private static final Logger log = LoggerFactory.getLogger(BotConfig.class);

    private final BotProperties botProperties;

    final private Map<String, Command> commandMap;

    @Autowired
    public BotConfig(BotProperties botProperties, Commands commands){
        this.botProperties = botProperties;
        this.commandMap = commands.getCommandsMap();
    }

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = null;

        try {
            client = DiscordClientBuilder.create(botProperties.getBotToken())
                    .build()
                    .login()
                    .block();

            for (EventListener<T> listener : eventListeners) {
                client.on(listener.getEventType())
                        .flatMap(listener::execute)
                        .onErrorResume(listener::handleError)
                        .subscribe();
            }
        } catch (Exception exception) {
            log.error("Be sure to use a valid bot token!", exception);
        }

        return client;

    }


    public Set<String> getAllowedCommands(){
        return commandMap.keySet();
    }

    public Command getCommandAction(String command){
        return commandMap.get(command);
    }
}
