package com.kt.game.goodgame.outergame.repository;

import com.kt.game.goodgame.outergame.domain.GameHistory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GameHistoryRepository extends ReactiveCrudRepository<GameHistory, String> {
    /**
     * 게임 이력 목록 전체 조회
     * order by created_at
     */
    @Query("""
                SELECT game_id
                     , left_user_id
                     , right_user_id
                     , winner_flag
                     , created_at
                     , game_status_code
                FROM TB_KTGAME_GAME_HISTORY
                ORDER BY created_at ASC
            """)
    Flux<GameHistory> retrieveGameHistories();

    /**
     * gameId 로 게임 이력 단 건 조회
     */
    @Query("""
                SELECT game_id
                     , left_user_id
                     , right_user_id
                     , winner_flag
                     , created_at
                     , game_status_code
                FROM TB_KTGAME_GAME_HISTORY
                WHERE game_id = :gameId
            """)
    Mono<GameHistory> retrieveGameHistoryByGameId(String gameId);

    /**
     * 게임 이력 삭제
     */
    @Query("""
                DELETE FROM TB_KTGAME_GAME_HISTORY
                WHERE game_id = :gameId
            """)
    Mono<Integer> deleteGameHistory(String gameId);
}
