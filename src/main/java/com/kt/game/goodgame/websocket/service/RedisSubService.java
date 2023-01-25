package com.kt.game.goodgame.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.game.goodgame.websocket.domain.GameMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RedisSubService implements MessageListener {
    public static List<String> messageList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            GameMessage gameMessage = mapper.readValue(message.getBody(), GameMessage.class);
//            messageList.add(message.toString());
//
//            System.out.println("받은 메시지 = " + message.toString());
//            System.out.println("gameMessage.getGameId() = " + gameMessage.getGameId());
//            System.out.println("gameMessage.getUser1Id() = " + gameMessage.getUser1Id());
//            System.out.println("gameMessage.getUser2Id() = " + gameMessage.getUser2Id());
//            System.out.println("gameMessage.getCurrentUserId() = " + gameMessage.getCurrentUserId());
//            System.out.println("gameMessage.getGameScore() = " + gameMessage.getGameScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
