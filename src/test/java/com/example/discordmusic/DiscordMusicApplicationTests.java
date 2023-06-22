package com.example.discordmusic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {DiscordMusicApplication.class, BotConfig.class})
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class DiscordMusicApplicationTests {


    @Autowired
    private BotConfig botConfig;
    @Test
    void contextLoads() {
    }

    @Test
    public void commandsLoadProperly(){
        System.out.println(botConfig.getAllowedCommands());
    }

}
