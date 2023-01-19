package com.kt.game.goodgame.waitinggame.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kt.game.goodgame.constants.GoodGameConstant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("tb_ktgame_room")
public class Room {
    private String gameId;
    private String roomName;
    private String leftParticipant;
    private String rightParticipant;
    private Integer countOfParticipants;
    private String roomStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = GoodGameConstant.DEFAULT_DATE_TIME_FORMAT)
    @CreatedDate
    private LocalDateTime createdAt;

}
