package com.kt.game.goodgame.play.repository;

import com.kt.game.goodgame.play.domain.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {

    @Tailable
    Flux<Game> findByGameId(String gameId);
}
