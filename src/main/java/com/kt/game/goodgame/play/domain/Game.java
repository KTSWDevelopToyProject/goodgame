package com.kt.game.goodgame.play.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "game")
public class Game {
    @Id
    private String id;
    private Integer gameId;
    private String user1Id;
    private String user2Id;
    private String currentUserId;
    private String gameScore;
    private String status; // G : go, S : stop, E : end

}
