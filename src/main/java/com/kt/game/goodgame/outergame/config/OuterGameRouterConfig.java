package com.kt.game.goodgame.outergame.config;

import com.kt.game.goodgame.outergame.handler.GameHistoryHandler;
import com.kt.game.goodgame.outergame.handler.MemberHandler;
import com.kt.game.goodgame.rating.handler.RatingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class OuterGameRouterConfig {

    private final MemberHandler memberHandler;
    private final GameHistoryHandler gameHistoryHandler;
    private final RatingHandler ratingHandler;

    /**
     * Member RouterFunction
     */
    @Bean(value = "InnerGameRouter")
    public RouterFunction<ServerResponse> router() {
        return RouterFunctions.route()
                .path("/member", builder -> builder
                        .GET("", memberHandler::retrieveMembers)
                        .GET("/user-id/{userId}", memberHandler::retrieveMemberByUserId)
                        .GET("/user-name/{userName}", memberHandler::retrieveMemberByUserName)
                        .POST("", memberHandler::createMember)
                        .PUT("/user-id/{userId}", memberHandler::updateMember)
                        .DELETE("/user-id/{userId}", memberHandler::deleteMember)
                )
                .path("/game-history",builder -> builder
                        .GET("", gameHistoryHandler::retrieveGameHistories)
                        .GET("/game-id/{gameId}", gameHistoryHandler::retrieveGameHistoryByGameId)
                        .POST("", gameHistoryHandler::createGameHistory)
                        .DELETE("/game-id/{gameId}", gameHistoryHandler::deleteGameHistory)
                )
                .path("/rating", builder -> builder
                        .GET("/{userId}", ratingHandler::retrieveRatingByUserId)
                        .POST("", ratingHandler::upsertRating)
                )
                .build();
    }

}
