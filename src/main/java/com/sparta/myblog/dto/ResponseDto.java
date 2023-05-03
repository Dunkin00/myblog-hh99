package com.sparta.myblog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.myblog.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private int statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ResponseDto<T> setSuccess(String message, T data){
        return new ResponseDto<>(200, message, data);
    }
}