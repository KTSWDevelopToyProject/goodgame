package com.kt.game.goodgame.innergame.repository;

import com.kt.game.goodgame.innergame.domain.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {

    @Tailable
    Flux<Game> findByGameId(String gameId);
}
