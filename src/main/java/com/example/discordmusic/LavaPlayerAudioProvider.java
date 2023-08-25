package com.example.discordmusic;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import discord4j.voice.AudioProvider;

import java.nio.ByteBuffer;

public final class LavaPlayerAudioProvider extends AudioProvider {

    private final AudioPlayer player;
    private final MutableAudioFrame frame = new MutableAudioFrame();

    public LavaPlayerAudioProvider(final AudioPlayer player) {
        // Allocate a ByteBuffer for Discord4J's AudioProvider to hold audio data
        // for Discord
        super(
                ByteBuffer.allocate(
                        StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()
                )
        );
        // Set LavaPlayer's MutableAudioFrame to use the same buffer as the one we
        // just allocated
        frame.setBuffer(getBuffer());
        this.player = player;
    }

    @Override
    public boolean provide() {
        final boolean didProvide = player.provide(frame);
        if (didProvide) {
            getBuffer().flip();
        }
        return didProvide;
    }
}