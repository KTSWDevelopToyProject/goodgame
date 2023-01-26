package com.kt.game.goodgame.innergame.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "game")
public class Game {
    @Id
    private String id;
    private String gameId;
    private String leftParticipant;
    private String rightParticipant;
    private String currentUserId;
    private String gameScore;
    private String status; // A: Access, G : go, S : stop, E : end

}
