package com.example.discordmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DiscordMusicApplication {

    public static void main(String[] args) {

        SpringApplication.run(DiscordMusicApplication.class, args);


        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Thread :" + Thread.currentThread() + "interrupted with " + e.getCause());
        }
    }
}
