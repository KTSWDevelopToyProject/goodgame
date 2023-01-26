package com.kt.game.goodgame.innergame.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.game.goodgame.websocket.domain.GameMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StompSocketService implements MessageListener {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(GameMessage gameMessage) {
        redisTemplate.convertAndSend("topic1", gameMessage);

    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        List<String> messageList = new ArrayList<>();

        try {

//            String message2 = (String) redisTemplate.getStringSerializer()
//                    .deserialize(message.getBody());
//
//            String data = mapper.readValue(message2, String.class);
//            System.out.println("1-1 : " + data);

            GameMessage gameMessage = mapper.readValue(message.getBody(), GameMessage.class);
            messageList.add(message.toString());

            System.out.println("2-1 : 받은 메시지 = " + message.toString());
            System.out.println("gameMessage.getGameId() = " + gameMessage.getGameId());
            System.out.println("gameMessage.getUser1Id() = " + gameMessage.getUser1Id());
            System.out.println("gameMessage.getUser2Id() = " + gameMessage.getUser2Id());
            System.out.println("gameMessage.getCurrentUserId() = " + gameMessage.getCurrentUserId());
            System.out.println("gameMessage.getGameScore() = " + gameMessage.getGameScore());
            simpMessagingTemplate.convertAndSend("/sub", gameMessage); // 1.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
