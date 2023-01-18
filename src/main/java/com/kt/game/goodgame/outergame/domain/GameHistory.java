package com.kt.game.goodgame.outergame.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kt.game.goodgame.constants.GoodGameConstant;
import com.kt.game.goodgame.constants.TableConstant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(TableConstant.GAME_HISTORY)
public class GameHistory {
    private String gameId;
    private String leftUserId;
    private String rightUserId;
    private String winnerFlag;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = GoodGameConstant.MILLISECOND_DATE_TIME_FORMAT)
    @CreatedDate
    private LocalDateTime createdAt;
    private String gameStatusCode;
}
