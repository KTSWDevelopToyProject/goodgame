package com.kt.game.goodgame.rating.repository;

import com.kt.game.goodgame.rating.domain.Rating;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RatingRepository extends ReactiveCrudRepository<Rating, String> {

    @Query("""
                SELECT rating_id
                     , user_id
                     , win
                     , total
                FROM tb_ktgame_game_rating
                WHERE user_id = :userId
            """)
    Mono<Rating> retrieveRatingByUserId(String userId);
}
