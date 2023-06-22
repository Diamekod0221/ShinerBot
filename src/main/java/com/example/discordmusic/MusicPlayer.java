package com.example.discordmusic;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MusicPlayer {
    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;

    public MusicPlayer(){
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        player = playerManager.createPlayer();
    }

    public void play(String query){
        playerManager.loadItem(query, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                player.playTrack(audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                if(!audioPlaylist.getTracks().isEmpty()){
                    player.playTrack(audioPlaylist.getTracks().get(0));
                }

            }

            @Override
            public void noMatches() {
                System.out.println("No biczes?");
            }

            @Override
            public void loadFailed(FriendlyException e) {
                System.out.println("Bolcowanie przegrane :" + e.getMessage());

            }
        });
    }

    public AudioPlayer getPlayer(){
        return player;
    }


}
