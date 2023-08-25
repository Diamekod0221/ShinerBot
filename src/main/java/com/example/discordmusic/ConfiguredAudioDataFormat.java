package com.example.discordmusic;

import com.sedmelluq.discord.lavaplayer.format.OpusAudioDataFormat;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "application.player.config.frameBuffer.audioDataFormat")
public class ConfiguredAudioDataFormat extends OpusAudioDataFormat {

    private int channels;
    private int sampleRate;
    private int chunkSampleCount;
    public ConfiguredAudioDataFormat(int channelCount, int sampleRate, int chunkSampleCount) {
        super(channelCount, sampleRate, chunkSampleCount);
    }

}
