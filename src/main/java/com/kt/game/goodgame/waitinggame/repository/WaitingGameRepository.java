package com.kt.game.goodgame.waitinggame.repository;

import com.kt.game.goodgame.waitinggame.domain.Room;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WaitingGameRepository extends ReactiveMongoRepository<Room, String> {

    /**
     * 룸 목록 전체 조회
     */
    @Tailable
    @Query("{}")
    Flux<Room> findAll();



    /**
     * roomStatus condition 에 따른 룸 목록 조회
     */
    @Tailable
    @Query(value = """
                        {roomStatus: ?0}
                    """)
    Flux<Room> retrieveRoomsByRoomStatus(String roomStatus);

}
