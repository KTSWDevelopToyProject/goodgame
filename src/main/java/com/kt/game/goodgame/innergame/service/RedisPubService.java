package com.kt.game.goodgame.innergame.service;

import com.kt.game.goodgame.innergame.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(ChannelTopic topic, Game game) {
        redisTemplate.convertAndSend(topic.getTopic(), game);

    }
}
