package com.kt.game.goodgame.innergame.controller;

import com.kt.game.goodgame.innergame.domain.Game;
import com.kt.game.goodgame.innergame.service.StompSocketService;
import com.kt.game.goodgame.websocket.domain.GameMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class StompSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StompSocketService stompSocketService;
    @MessageMapping("/test")
//    @SendTo("/sub/{key}")
//    @MessageMapping("/test")  // 1. test > sub
    public Object test(@RequestBody GameMessage msg) {
        System.out.println("msg : " + msg.toString());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("test", "tetete");
        stompSocketService.sendMessage(msg);
//        simpMessagingTemplate.convertAndSend("/sub", payload); // 1.
        return payload;
    }

}
