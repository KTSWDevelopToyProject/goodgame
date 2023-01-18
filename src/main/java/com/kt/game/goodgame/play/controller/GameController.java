package com.kt.game.goodgame.play.controller;

import com.kt.game.goodgame.play.domain.Game;
import com.kt.game.goodgame.play.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
public class GameController {
    private final GameRepository gameRepository;

    @CrossOrigin
    @GetMapping(value = "/game/{gameId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Game> getGameScore(@PathVariable String gameId) {
        // OrderByGameScoreAsc
        return gameRepository.findByGameId(gameId).subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @PostMapping(value = "/game/{gameId}")
    public Mono<Game> setGameScore(@PathVariable String gameId, @RequestBody Game game) {
        game.setGameId(gameId);
        return gameRepository.save(game);
    }
}
