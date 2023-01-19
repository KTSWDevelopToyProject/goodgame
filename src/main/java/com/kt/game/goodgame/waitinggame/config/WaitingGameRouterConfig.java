package com.kt.game.goodgame.waitinggame.config;

import com.kt.game.goodgame.waitinggame.handler.WaitingGameHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class WaitingGameRouterConfig {

    private final WaitingGameHandler waitingGameHandler;

    /**
     * WaitingGame RouterFunction
     */
    @Bean(value = "WaitingGameRouter")
    public RouterFunction<ServerResponse> router() {
        return RouterFunctions.route()
                .path("/waiting-rooms", builder -> builder
                        .GET("", waitingGameHandler::retrieveRoomsWithRoomStatus)
                        .POST("", waitingGameHandler::createRoom)
                )
                .build();
    }

}
