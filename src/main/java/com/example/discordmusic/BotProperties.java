package com.example.discordmusic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "application")
public class BotProperties {
    private String botToken;

    private String clientToken;
    private List<String> allowedCommands = new ArrayList<>();

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken){
        this.botToken = botToken;
    }

    public void setClientToken(String clientToken){
        this.clientToken = clientToken;
    }
    public List<String> getAllowedCommands() {
        return allowedCommands;
    }

    public void setAllowedCommands(List<String> allowedCommands) {
        this.allowedCommands = allowedCommands;
    }
}
