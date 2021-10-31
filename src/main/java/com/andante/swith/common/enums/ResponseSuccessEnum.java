package com.andante.swith.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseSuccessEnum implements EnumType {

    SUCCESS("0000","OK");

    private String code;
    private String message;

    @Override
    public String getId() {
        return name();
    }
}
