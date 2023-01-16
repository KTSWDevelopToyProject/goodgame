package com.kt.game.goodgame.exception.handler;

import java.io.Serial;

public class BadRequestException extends BaseException {
    @Serial
    private static final long serialVersionUID = -4595724706596189485L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
