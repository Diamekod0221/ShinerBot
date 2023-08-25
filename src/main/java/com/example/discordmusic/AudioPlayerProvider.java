package com.example.discordmusic;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.voice.AudioProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AudioPlayerProvider {

    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;

    private AudioProvider provider;

    private FrameBufferConfig bufferConfig;

    @Autowired
    public AudioPlayerProvider(FrameBufferConfig bufferConfig){
        this.bufferConfig = bufferConfig;
        playerManager = new DefaultAudioPlayerManager();
        configurePlayerManager();
        player = playerManager.createPlayer();
        provider = new LavaPlayerAudioProvider(player);
    }

    private void configurePlayerManager(){
        playerManager.getConfiguration()
                .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);

        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    public AudioPlayerManager getPlayerManager() {
        return playerManager;
    }

    public AudioProvider getProvider() {
        return provider;
    }

    public FrameBufferConfig getBufferConfig() {
        return bufferConfig;
    }
}
