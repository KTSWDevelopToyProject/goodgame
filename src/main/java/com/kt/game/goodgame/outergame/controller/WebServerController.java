package com.kt.game.goodgame.outergame.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webclient")
@Slf4j
public class WebServerController {
    @GetMapping("/{param}")
    String testWebClient(@PathVariable String param,
                         @RequestHeader HttpHeaders headers,
                         @CookieValue(name = "httpclient-type", required = false, defaultValue = "undefined") String httpClientType) {
        log.info(">>>> Cookie 'httpclient-type={}", httpClientType);

        headers.forEach((key, value) -> log.info(String.format(">>>> Header %s => %s", key, value)));

        log.info("### Received: /webclient/" + param);
        String msg = param + " => Working successfully !!!";
        log.info("### Sent : " + msg);
        return msg;
    }
}
