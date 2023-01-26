package com.kt.game.goodgame.websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kt.game.goodgame.websocket.domain.GameMessage;
import com.kt.game.goodgame.websocket.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class RedisController {
    private final RedisService redisService;

//    @PostMapping("api/redisStringTest")
//    public String sendString(@RequestBody GameMessage gameMessage) {
//        redisService.setRedisStringValue(gameMessage);
//
//        redisService.getRedisStringValue("sender");
//        redisService.getRedisStringValue("context");
//
//        return "success";
//    }

    @PostMapping("api/redisTest")
    public String send(@RequestBody GameMessage gameMessage) throws JsonProcessingException {
        redisService.setRedisValue(gameMessage);

        String key = gameMessage.getGameId();
        GameMessage gameMessage1 = redisService.getRedisValue(key, GameMessage.class);

        return gameMessage1.toString();
    }

    @PostMapping("api/chat")
    public String pubSub(@RequestBody GameMessage gameMessage) {
        //메시지 보내기
//        redisPubService.sendMessage(gameMessage);

        return "success";
    }

    @MessageMapping("/chat/message")
    public void message(GameMessage gameMessage) {


        redisService.sendMessage(gameMessage);
    }
}
