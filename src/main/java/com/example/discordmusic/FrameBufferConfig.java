package com.example.discordmusic;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@ConfigurationProperties(prefix = "application.player.config.frameBuffer")
public class FrameBufferConfig {

    private final int bufferDuration;
    private AudioDataFormat audioFormat;
    private final AtomicBoolean stoppingSignal = new AtomicBoolean(false);

    @Autowired
    public FrameBufferConfig(@Value("duration") int bufferDuration, ConfiguredAudioDataFormat audioFormat){
        this.bufferDuration = bufferDuration;
        this.audioFormat = audioFormat;
    }

    public int getBufferDuration() {
        return bufferDuration;
    }

    public AtomicBoolean getStoppingSignal() {
        return stoppingSignal;
    }

    public AudioDataFormat getAudioFormat() {
        return audioFormat;
    }


}
