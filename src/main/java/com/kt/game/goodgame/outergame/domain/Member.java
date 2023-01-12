package com.kt.game.goodgame.outergame.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kt.game.goodgame.GoodGameConstant;
import com.kt.game.goodgame.TableConstant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(TableConstant.MEMBER_TABLE)
public class Member {

    private String userId;
    private String userName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = GoodGameConstant.MILLISECOND_DATE_TIME_FORMAT)
    @CreatedDate
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = GoodGameConstant.MILLISECOND_DATE_TIME_FORMAT)
    private LocalDateTime deletedAt;
    private String accountActivatedYn;
    private String loginYn;
}
