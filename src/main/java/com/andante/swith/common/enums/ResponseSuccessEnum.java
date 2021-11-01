package com.andante.swith.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseSuccessEnum implements EnumType {

    SUCCESS("200","OK");

    private String status;
    private String message;

    @Override
    public String getId() {
        return name();
    }
}
