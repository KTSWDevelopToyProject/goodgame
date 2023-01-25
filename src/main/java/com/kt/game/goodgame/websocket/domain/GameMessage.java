package com.kt.game.goodgame.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameMessage {

    private String gameId;
    private String user1Id;
    private String user2Id;
    private String currentUserId;
    private String gameScore;
    private String status; // A: Access, G : go, S : stop, E : end
}