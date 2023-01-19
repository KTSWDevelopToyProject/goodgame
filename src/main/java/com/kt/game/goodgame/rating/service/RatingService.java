package com.kt.game.goodgame.rating.service;

import com.kt.game.goodgame.exception.handler.BadRequestException;
import com.kt.game.goodgame.rating.domain.Rating;
import com.kt.game.goodgame.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RatingService {
    private final RatingRepository ratingRepository;

    public Mono<Rating> retrieveRatingByUserId(String userId) {
        try {
            return ratingRepository.retrieveRatingByUserId(userId);
        } catch (Exception e) {
            return Mono.error(new BadRequestException("멤버 ID로 멤버 조회하는 작업을 실패하였습니다.", e.getCause()));
        }
    }

    public Mono<Rating> upsertRatingByUserId(String userId, Rating rating) {
        return ratingRepository.retrieveRatingByUserId(userId)
                .flatMap(r -> {
                    log.info("Rating : " + r);
                    r.setTotal(rating.getTotal());
                    r.setWin(rating.getWin());
                    r.setRatingId(r.getRatingId());
                    return ratingRepository.save(r);
        }).switchIfEmpty(ratingRepository.save(rating));
    }
}
