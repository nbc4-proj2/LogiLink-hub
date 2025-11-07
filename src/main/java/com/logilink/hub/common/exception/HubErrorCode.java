package com.logilink.hub.common.exception;

import com.sparta.logilinkcommon.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HubErrorCode implements ErrorCode {

    HUB_NOT_FOUND("HUB0001", "해당 허브를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    HUB_NAME_DUPLICATE("HUB002", "이미 사용 중인 허브 이름입니다.", HttpStatus.CONFLICT),
    HUB_ROUTE_NOT_FOUND("HUB003", "해당 허브 이동 경로를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

    HubErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
