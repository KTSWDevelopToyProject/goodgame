package com.kt.game.goodgame.innergame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class StompSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/test/{key}")
    @SendTo("/sub/{key}")
//    @MessageMapping("/test")  // 1. test > sub
    public Object test(String msg) {
        System.out.println("msg : " + msg);
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("test", "tetete");
//        simpMessagingTemplate.convertAndSend("/sub", payload); // 1.
        return payload;
    }

}
