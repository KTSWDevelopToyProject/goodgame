package com.kt.game.goodgame.exception.handler;

import java.io.Serial;
import java.io.Serializable;

public class BaseException extends RuntimeException implements Serializable{
    @Serial
    private static final long serialVersionUID = 4745572785109577086L;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
