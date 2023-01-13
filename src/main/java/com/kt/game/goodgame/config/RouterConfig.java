package com.kt.game.goodgame.config;

import com.kt.game.goodgame.outergame.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final MemberHandler memberHandler;

    /**
     * Member Router
     */
    @Bean
    public RouterFunction<ServerResponse> memberRouter() {
        return RouterFunctions.route()
                .path("/member", builder -> builder
                        .GET("", memberHandler::retrieveMembers)
                        .GET("/user-id/{userId}", memberHandler::retrieveMemberByUserId)
                        .GET("/user-name/{userName}", memberHandler::retrieveMemberByUserName)
                        .POST("", memberHandler::createMember)
                        .PUT("/user-id/{userId}", memberHandler::updateMember)
                        .DELETE("/user-id/{userId}", memberHandler::deleteMember)
                )
                .build();
    }

}
