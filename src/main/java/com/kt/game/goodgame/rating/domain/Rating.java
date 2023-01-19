package com.kt.game.goodgame.rating.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kt.game.goodgame.constants.TableConstant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(TableConstant.GAME_RATING)
public class Rating {
    @Id
    private Integer ratingId;


    private String userId;
    private Integer win;
    private Integer total;
    private Double winningRate;
}
