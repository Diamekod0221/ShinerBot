package com.example.discordmusic;

import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class Commands {

    private final Map<String, Command> commandsMap;

    //populating commands via reactive blocks.
    @Autowired
    public Commands(AudioPlayerProvider provider, TrackScheduler scheduler){
        commandsMap = new HashMap<>();

        addJoin(provider);
        addPing();
        addPlay(scheduler, provider);
        addCall();
    }

    private void addPing(){
        commandsMap.put("ping", event -> event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("Pong!"))
                .then());
    }

    private void addJoin(AudioPlayerProvider provider){
        commandsMap.put("join", event -> Mono.justOrEmpty(event.getMember())
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                // join returns a VoiceConnection which would be required if we were
                // adding disconnection features, but for now we are just ignoring it.
                .flatMap(channel -> channel.join(spec -> spec.setProvider(provider.getProvider())))
                .then());
    }

    private void addPlay(TrackScheduler scheduler, AudioPlayerProvider provider){
        commandsMap.put("play", event -> Mono.justOrEmpty(event.getMessage().getContent())
                .map(content -> Arrays.asList(content.split(" ")))
                .doOnNext(command -> provider.getPlayerManager().loadItem(command.get(1), scheduler))
                .then());

    }

    private void addCall(){
        commandsMap.put("shiner", event ->
        Mono.just(event.getMessage()).filter(mess -> mess.getAuthor().map(user -> !user.isBot()).orElse(false)).flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Them connections mighty heavy' sir")).then());

    }

    public Map<String, Command> getCommandsMap() {
        return Map.copyOf(commandsMap);
    }
}
