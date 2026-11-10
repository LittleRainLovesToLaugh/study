package com.cenyao.commerce.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 业务异常：映射为 HTTP 状态 + 响应体 code。
 */
@Getter
public class BizException extends RuntimeException {

    private final int code;
    private final HttpStatus httpStatus;

    public BizException(String message) {
        this(40001, HttpStatus.BAD_REQUEST, message);
    }

    public BizException(int code, HttpStatus httpStatus, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public static BizException unauthorized(String message) {
        return new BizException(40100, HttpStatus.UNAUTHORIZED, message);
    }

    public static BizException forbidden(String message) {
        return new BizException(40300, HttpStatus.FORBIDDEN, message);
    }

    public static BizException notFound(String message) {
        return new BizException(40400, HttpStatus.NOT_FOUND, message);
    }
}
