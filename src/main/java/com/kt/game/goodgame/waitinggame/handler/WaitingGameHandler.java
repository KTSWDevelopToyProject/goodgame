package com.kt.game.goodgame.waitinggame.handler;

import com.kt.game.goodgame.waitinggame.domain.Room;
import com.kt.game.goodgame.waitinggame.service.WaitingGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WaitingGameHandler {

    private final WaitingGameService waitingGameService;

    /**
     * roomStatus condition 에 따른 룸 목록 조회
     */
    public Mono<ServerResponse> retrieveRoomsWithRoomStatus(ServerRequest serverRequest) {
        String roomStatus = serverRequest.queryParam("roomStatus").orElse("");
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(roomStatus == "" ? waitingGameService.retrieveRooms(): waitingGameService.retrieveRoomsByRoomStatus(roomStatus), Room.class);
    }

    /**
     * room 생성
     */
    public Mono<ServerResponse> createRoom(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Room.class)
                .flatMap(room -> {
                    room.setGameId(UUID.randomUUID().toString());
                    return waitingGameService.createRoom(room);
                })
                .flatMap(room -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(room));
    }

}
