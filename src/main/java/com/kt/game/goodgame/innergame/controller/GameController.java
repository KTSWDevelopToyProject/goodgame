package com.kt.game.goodgame.innergame.controller;

import com.kt.game.goodgame.innergame.domain.Game;
import com.kt.game.goodgame.innergame.repository.GameRepository;
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
    @PostMapping(value = "/game")
    public Mono<Game> setGameScore(@RequestBody Game game) {
//        game.setGameId(gameId);
        return gameRepository.save(game);
    }
}
