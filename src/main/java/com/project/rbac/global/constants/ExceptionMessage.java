package com.project.rbac.global.constants;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    NOT_FOUND_USER_ID("존재하지 않는 ID입니다."),
    WRONG_PASSWORD("비밀번호를 틀리셨습니다."),
    EXIST_USER_ID("이미 존재하는 ID입니다."),
    MALFORMED_JWT_EXCEPTION("잘못된 JWT 형식입니다."),
    EXPIRED_JWT_EXCEPTION("만료된 토큰입니다."),
    SIGNATURE_EXCEPTION("잘못된 서명입니다."),
    JWT_EXCEPTION("유효하지 않는 토큰입니다."),
    GENERAL_EXCEPTION("서버 오류 발생: "),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION("필수 요청 파라미터 누락: "),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION("잘못된 파라미터 타입: "),
    ACCESS_DENIED_EXCEPTION("접근 권한이 없습니다."),
    AUTHENTICATION_EXCEPTION("인증에 실패했습니다."),
    EXCEPTION_TOKEN("토큰 검증 중 오류가 발생했습니다."),
    EXCEPTION_LOG_OUT("로그아웃된 토큰입니다."),
    NOT_FOUND_REFRESH_TOKEN("존재하지않는 Refresh_token 입니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}

