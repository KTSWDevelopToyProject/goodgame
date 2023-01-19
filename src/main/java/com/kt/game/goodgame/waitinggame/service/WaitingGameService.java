package com.kt.game.goodgame.waitinggame.service;

import com.kt.game.goodgame.waitinggame.domain.Room;
import com.kt.game.goodgame.waitinggame.repository.WaitingGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WaitingGameService {

    private final WaitingGameRepository waitingGameRepository;

    /**
     * 룸 목록 전체 조회
     */
    public Flux<Room> retrieveRooms() {
        return waitingGameRepository.findAll();
    }

    /**
     * roomStatus condition 에 따른 룸 목록 조회
     */
    public Flux<Room> retrieveRoomsByRoomStatus(String roomStatus) {
        return waitingGameRepository.retrieveRoomsByRoomStatus(roomStatus);
    }

    /**
     * room 생성
     */
    public Mono<Room> createRoom(Room room) {
        return waitingGameRepository.save(room);
    }

}
