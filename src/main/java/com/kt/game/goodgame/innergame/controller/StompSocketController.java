package com.kt.game.goodgame.innergame.controller;

import com.kt.game.goodgame.innergame.domain.Game;
import com.kt.game.goodgame.innergame.service.StompSocketService;
import com.kt.game.goodgame.websocket.domain.GameMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class StompSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StompSocketService stompSocketService;
    @MessageMapping("/test/{gameId}")
//    @SendTo("/sub/{key}")
//    @MessageMapping("/test")  // 1. test > sub
    public Object test(@PathVariable String gameId, @RequestBody Game game) {
        System.out.println("msg : " + game.toString());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("test", "tetete");
        stompSocketService.sendMessage(gameId, game);
//        simpMessagingTemplate.convertAndSend("/sub", payload); // 1.
        return payload;
    }

}
