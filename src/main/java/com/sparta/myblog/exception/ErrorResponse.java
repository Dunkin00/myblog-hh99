package com.sparta.myblog.exception;

import com.sparta.myblog.entity.StatusEnum;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int statusCode;
    private final String message;

    public ErrorResponse(StatusEnum statusEnum){
        this.statusCode = statusEnum.getStatus().value();
        this.message = statusEnum.getMessage();
    }

    public ErrorResponse(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.message = msg;
    }
}
