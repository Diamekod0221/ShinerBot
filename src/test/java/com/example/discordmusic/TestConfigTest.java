package com.example.discordmusic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = BotProperties.class)
public class TestConfigTest {

    @Autowired
    private BotConfig config;

    @Test
    public void testListLoad(){
        System.out.println(config.getAllowedCommands());
    }

    @Test
    public void testTokenLoad(){
        System.out.println(config.botProperties.getBotToken());
    }

}
