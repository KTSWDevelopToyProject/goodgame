package com.kt.game.goodgame.rating.handler;

import com.kt.game.goodgame.rating.domain.Rating;
import com.kt.game.goodgame.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RatingHandler {
    private final RatingService ratingService;

    public Mono<ServerResponse> retrieveRatingByUserId(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(ratingService.retrieveRatingByUserId(userId), Rating.class);
    }

    public Mono<ServerResponse> upsertRating(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Rating.class)
                .flatMap(rating -> ratingService.upsertRatingByUserId(rating.getUserId(), rating))
                .flatMap(rating -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(rating), Rating.class));
    }
}
