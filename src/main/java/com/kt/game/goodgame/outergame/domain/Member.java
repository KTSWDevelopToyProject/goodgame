package com.kt.game.goodgame.outergame.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private String userId;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private String accountActivatedYn;
    private String loginYn;
}
