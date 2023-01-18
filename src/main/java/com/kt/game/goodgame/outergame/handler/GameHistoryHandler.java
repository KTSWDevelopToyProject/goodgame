package com.kt.game.goodgame.outergame.handler;

import com.kt.game.goodgame.outergame.domain.GameHistory;
import com.kt.game.goodgame.outergame.service.GameHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GameHistoryHandler {
    private final GameHistoryService gameHistoryService;

    /**
     * 게임 이력 목록 전체 조회
     */
    public Mono<ServerResponse> retrieveGameHistories(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(gameHistoryService.retrieveGameHistories(), GameHistory.class);
    }

    /**
     * gameId 로 게임 이력 단 건 조회
     */
    public Mono<ServerResponse> retrieveGameHistoryByGameId(ServerRequest serverRequest) {
        String gameId = serverRequest.pathVariable("gameId");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(gameHistoryService.retrieveGameHistoryByGameId(gameId), GameHistory.class);
    }

    /**
     * 게임 이력 생성
     */
    public Mono<ServerResponse> createGameHistory(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(GameHistory.class)
                .flatMap(gameHistory -> {
                    gameHistory.setGameId(UUID.randomUUID().toString());
                    return gameHistoryService.createGameHistory(gameHistory);
                })
                .flatMap(gameHistory -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(gameHistory), GameHistory.class));
    }

    /**
     * 게임 이력 삭제
     */
    public Mono<ServerResponse> deleteGameHistory(ServerRequest serverRequest) {
        String gameId = serverRequest.pathVariable("gameId");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(gameHistoryService.deleteGameHistory(gameId), Integer.class);
    }
}
