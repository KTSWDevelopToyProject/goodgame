package com.kt.game.goodgame.outergame.service;

import com.kt.game.goodgame.exception.handler.BadRequestException;
import com.kt.game.goodgame.outergame.domain.GameHistory;
import com.kt.game.goodgame.outergame.repository.GameHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameHistoryService {
    private final GameHistoryRepository gameHistoryRepository;

    /**
     * 게임 이력 목록 전체 조회
     */
    public Flux<GameHistory> retrieveGameHistories() {
        try {
            return gameHistoryRepository.retrieveGameHistories();
        } catch (Exception e) {
            return Flux.error(new BadRequestException("게임 이력 목록 조회 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * gameId 로 게임 이력 단 건 조회
     */
    public Mono<GameHistory> retrieveGameHistoryByGameId(String gameId) {
        try {
            return gameHistoryRepository.retrieveGameHistoryByGameId(gameId);
        } catch (Exception e) {
            return Mono.error(new BadRequestException("게임 이력 조회 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * 게임 이력 생성
     */
    public Mono<GameHistory> createGameHistory(GameHistory gameHistory) {
        try {
            return gameHistoryRepository.save(gameHistory);
        } catch (Exception e) {
            return Mono.error(new BadRequestException("게임 이력 생성 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * 게임 이력 삭제
     */
    public Mono<Integer> deleteGameHistory(String gameId) {
        try {
            return gameHistoryRepository.deleteGameHistory(gameId);
        } catch (Exception e) {
            return Mono.error(new BadRequestException("게임 이력 삭제 작업을 실패하였습니다.", e.getCause()));
        }
    }
}
