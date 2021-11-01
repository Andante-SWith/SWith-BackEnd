package com.andante.swith.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseFailEnum implements EnumType {

    NO_DATA("-1", "사용자 정보를 찾을 수 없습니다."),
    SERVER_ERROR("-2", "서버 에러입니다."),
    LOGIN_ERROR("-3", "이메일 또는 비밀번호가 틀립니다.");


    private String status;
    private String message;

    @Override
    public String getId() {
        return name();
    }
}
