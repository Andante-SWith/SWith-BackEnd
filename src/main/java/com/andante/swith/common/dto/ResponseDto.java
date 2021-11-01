package com.andante.swith.common.dto;

import com.andante.swith.common.enums.ResponseFailEnum;
import com.andante.swith.common.enums.ResponseSuccessEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@Slf4j
public class ResponseDto<T> {

    private String status;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(T data) {
        ResponseDto<T> response =
                ResponseDto.<T>builder().status(ResponseSuccessEnum.SUCCESS.getStatus())
                        .message(ResponseSuccessEnum.SUCCESS.getMessage()).data(data).build();
        log.info("Success API Response: {}", response.toString());
        return response;
    }

    public static <T> ResponseDto<T> fail(String status, String message) {
        ResponseDto<T> response = ResponseDto.<T>builder().status(status).message(message).build();
        log.error("Failed API Response: {}", response.toString());
        return response;
    }

    public static <T> ResponseDto<T> fail(ResponseFailEnum responseFail) {
        return fail(responseFail.getStatus(), responseFail.getMessage());
    }
}
