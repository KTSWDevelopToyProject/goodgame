package com.kt.game.goodgame.innergame.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.game.goodgame.innergame.domain.Game;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class StompSocketService implements MessageListener {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisPubService redisPubService;
    private Map<String, ChannelTopic> channels;

    @PostConstruct
    public void init() {
        channels = new HashMap<>();
    }

    public void sendMessage(String gameId, Game game) {
//        redisTemplate.convertAndSend("topic1", gameMessage);
        ChannelTopic channelTopic = channels.get(gameId);
        if (channelTopic == null) {
            ChannelTopic channel = new ChannelTopic(gameId);
            redisMessageListenerContainer.addMessageListener(this, channel);
            channels.put(gameId, channel);
            redisPubService.sendMessage(channel, game);
        } else {
            redisPubService.sendMessage(channelTopic, game);
        }

    }

    public void deleteTopic(String gameId) {
        ChannelTopic channel = channels.get(gameId);
        redisMessageListenerContainer.removeMessageListener(this, channel);
        channels.remove(gameId);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        List<String> messageList = new ArrayList<>();

        try {

//            String message2 = (String) redisTemplate.getStringSerializer()
//                    .deserialize(message.getBody());
//
//            String data = mapper.readValue(message2, String.class);
//            log.info("1-1 : " + data);

            Game game = mapper.readValue(message.getBody(), Game.class);
            messageList.add(message.toString());

            log.info("2-1 : 받은 메시지 = " + message.toString());
            log.info("gameMessage.getGameId() = " + game.getGameId());
            log.info("gameMessage.getUser1Id() = " + game.getUser1Id());
            log.info("gameMessage.getUser2Id() = " + game.getUser2Id());
            log.info("gameMessage.getCurrentUserId() = " + game.getCurrentUserId());
            log.info("gameMessage.getGameScore() = " + game.getGameScore());
            simpMessagingTemplate.convertAndSend("/sub", game); // 1.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
