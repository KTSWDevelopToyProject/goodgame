package com.kt.game.goodgame.websocket.service;

import com.kt.game.goodgame.websocket.domain.GameMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(GameMessage gameMessage) {
        redisTemplate.convertAndSend("topic1", gameMessage);

    }
}
